package com.arsalan.garage.fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.arsalan.garage.R;
import com.arsalan.garage.activities.CameraGalleryActivity;
import com.arsalan.garage.activities.LoginActivity;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.PrefUtility;
import com.arsalan.garage.utils.Urls;
import com.arsalan.garage.utils.Utils;

public class PostAdTabFragment extends AdvertisementBaseFragment implements View.OnClickListener {

    private static final String TAG = "PostAdTabFragment";
    private int mCurrentImageSelection = -1;
    private Button mButtonLogin;
    private final int LOGIN = 197;

    public PostAdTabFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImagePaths = new String[MAX_ADV];
        mMakeAmericanArrayList = getSpinnerArrayList(R.array.car_sub_category_american_title, R.array.car_sub_category_american_code);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_post_ad_tab, container, false);
        mButtonLogin = (Button) rootView.findViewById(R.id.button_login);
        prepareLogin();
        if (PrefUtility.isLoggedIn()) {
            rootView.findViewById(R.id.relative_layout_login).setVisibility(View.GONE);
            rootView.findViewById(R.id.scroll_view_container).setVisibility(View.VISIBLE);
        } else {
            rootView.findViewById(R.id.relative_layout_login).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.scroll_view_container).setVisibility(View.GONE);
        }

        init(rootView);
        setRemoveImageIconTags();
        registerRemoveImageListeners();
        setCategoryAdapter();
        setSubCategoryAdapter(getSpinnerArrayList(R.array.car_sub_category_american_title, R.array.car_sub_category_american_code));
        return rootView;
    }

    private void prepareLogin() {
        if (PrefUtility.isLoggedIn()) {
            mButtonLogin.setText(R.string.logout);
        } else {
            mButtonLogin.setText(R.string.login);
        }
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mButtonLogin.getText().toString().equals("Login")) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, LOGIN);
                } else {
                    Utils.showToastMessage(getActivity(), "Logout Successfully");
                    PrefUtility.clearUsrPrefs();
                    mButtonLogin.setText(R.string.login);
                }

            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button_first_img:
                mCurrentImageSelection = 1;
                openPhotoOptions();
                break;
            case R.id.button_second_img:
                mCurrentImageSelection = 2;
                openPhotoOptions();
                break;
            case R.id.button_third_img:
                mCurrentImageSelection = 3;
                openPhotoOptions();
                break;
            case R.id.button_fourth_img:
                mCurrentImageSelection = 4;
                openPhotoOptions();
                break;
            case R.id.button_fifth_img:
                mCurrentImageSelection = 5;
                openPhotoOptions();
                break;
            case R.id.button_post_add:
                if (!Utils.isNetworkAvailable()) {
                    showSnackBar(getString(R.string.no_internet_connection));
                    return;
                }

                if (!PrefUtility.isLoggedIn()) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    return;
                }

                if (mImagesAdded == 0) {
                    showSnackBar(getString(R.string.there_is_no_image_to_post));
                    return;
                }

                if (TextUtils.isEmpty(mMakeRegion) || TextUtils.isEmpty(mMake) || TextUtils.isEmpty(mEditTextTitle.getText().toString()) || TextUtils.isEmpty(mEditTextMobile.getText().toString()) || TextUtils.isEmpty(mEditTextPrice.getText().toString()) || TextUtils.isEmpty(mEditTextModel.getText().toString()) || TextUtils.isEmpty(mEditTextDescription.getText().toString())) {
                    showSnackBar(getString(R.string.all_fields_are_compulsory));
                    return;
                }
                mProgressDialog = new ProgressDialog(getActivity());
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
                new LongOperation().execute();
                break;
            default:
                break;
        }
    }

    @Override
    String getUploadUrl() {
        String urlString = Urls.FORESALE_UPLOAD;
        if (mMakeRegion.equals(AppConstants.SCRAP)) {
            urlString = Urls.SCRAP_UPLOAD;
        }

        if (mMakeRegion.equals(AppConstants.MARINE)) {
            urlString = Urls.MARINE_UPLOAD;
        }

        if (mMakeRegion.equals(AppConstants.ACCESSORIES)) {
            urlString = Urls.ACCESSORIES_UPLOAD;
        }
        return urlString;
    }

    @Override
    void finishedUploading() {

    }

    @Override
    String getPostType() {
        return POST_TYPE_NEW;
    }

    private void openPhotoOptions() {
        Intent intent = new Intent(getActivity(), CameraGalleryActivity.class);
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case CHOOSE_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    String mProfileImagePath = data.getStringExtra(AppConstants.EXTRA_IMAGE_PATH);
                    mImagePaths[mCurrentImageSelection - 1] = mProfileImagePath;
                    if (mProfileImagePath != null) {
                        Bitmap bitmap = Utils.getBitmapFromPath(mProfileImagePath);
                        mImagesAdded++;
                        switch (mCurrentImageSelection) {
                            case 1:
                                ((ImageView) mButtonFirstImage.findViewById(R.id.imageview_ad)).setImageBitmap(bitmap);

                                break;
                            case 2:
                                ((ImageView) mButtonSecondImage.findViewById(R.id.imageview_ad)).setImageBitmap(bitmap);
                                break;
                            case 3:
                                ((ImageView) mButtonThirdImage.findViewById(R.id.imageview_ad)).setImageBitmap(bitmap);
                                break;
                            case 4:
                                ((ImageView) mButtonFourthImage.findViewById(R.id.imageview_ad)).setImageBitmap(bitmap);
                                break;
                            case 5:
                                ((ImageView) mButtonFifthImage.findViewById(R.id.imageview_ad)).setImageBitmap(bitmap);
                                break;
                            default:
                                break;
                        }
                        setAddImageTexVisibility(mCurrentImageSelection, View.GONE);
                        setRemoveImageIconVisibility(mCurrentImageSelection, View.VISIBLE);
                    } else {
                        Utils.showToastMessage(getActivity(), "Please choose another image.");
                    }
                }
                break;
            case LOGIN:
                if (resultCode == Activity.RESULT_OK) {
                    if (getView() != null) {
                        getView().findViewById(R.id.relative_layout_login).setVisibility(View.GONE);
                        getView().findViewById(R.id.scroll_view_container).setVisibility(View.VISIBLE);
                    }
                }
                break;
            default:
                break;
        }
    }
}
