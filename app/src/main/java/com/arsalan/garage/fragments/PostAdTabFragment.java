package com.arsalan.garage.fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.arsalan.garage.R;
import com.arsalan.garage.activities.CameraGalleryActivity;
import com.arsalan.garage.activities.LoginActivity;
import com.arsalan.garage.adapters.CustomSpinnerAdapter;
import com.arsalan.garage.adapters.NothingSelectedSpinnerAdapter;
import com.arsalan.garage.models.SpinnerItem;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.Logger;
import com.arsalan.garage.utils.PrefUtility;
import com.arsalan.garage.utils.Urls;
import com.arsalan.garage.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class PostAdTabFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "PostAdTabFragment";
    private Spinner mSpinnerCategory;
    private Spinner mSpinnerSubCategory;
    private static final int CHOOSE_PHOTO = 1000;
    private String mProfileImagePath = "";
    private int mCurrentImageSelection = -1;
    private String[] mImagePaths;
    private EditText mEditTextTitle;
    private EditText mEditTextMobile;
    private EditText mEditTextPrice;
    private EditText mEditTextModel;
    private EditText mEditTextDescription;
    //private String mMakeRegion = AppConstants.AMERICAN;
    private String mMakeRegion;
    private String mMake;
    private ArrayList<SpinnerItem> mMakeRegionArrayList;
    private ArrayList<SpinnerItem> mMakeAmericanArrayList;
    private ArrayList<SpinnerItem> mMakeEuropeanArrayList;
    private ArrayList<SpinnerItem> mMakeAsianArrayList;
    private ArrayList<SpinnerItem> mMakeScrapArrayList;
    private ArrayList<SpinnerItem> mMakeMarineArrayList;
    private ArrayList<SpinnerItem> mAccessoriesMarineArrayList;
    //private ProgressDialog mProgressDialog;
    private LinearLayout mLinearLayoutAddViewContainer;
    private View mButtonFirstImage;
    private View mButtonSecondImage;
    private View mButtonThirdImage;
    private View mButtonFourthImage;
    private View mButtonFifthImage;
    private int MAX_ADV = 5;
    private int mImagesAdded = 0;
    private ProgressDialog mProgressDialog;
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
        mLinearLayoutAddViewContainer = (LinearLayout) rootView.findViewById(R.id.linear_layout_add_view_container);
        mSpinnerCategory = (Spinner) rootView.findViewById(R.id.spinner_category);
        mSpinnerSubCategory = (Spinner) rootView.findViewById(R.id.spinner_sub_category);
        rootView.findViewById(R.id.button_post_add).setOnClickListener(this);
        mEditTextTitle = (EditText) rootView.findViewById(R.id.edittext_title);
        mEditTextMobile = (EditText) rootView.findViewById(R.id.edittext_mobile_no);
        mEditTextPrice = (EditText) rootView.findViewById(R.id.edittext_price);
        mEditTextModel = (EditText) rootView.findViewById(R.id.edittext_model);
        mEditTextDescription = (EditText) rootView.findViewById(R.id.edittext_description);
        mButtonFirstImage = rootView.findViewById(R.id.button_first_img);
        mButtonSecondImage = rootView.findViewById(R.id.button_second_img);
        mButtonThirdImage = rootView.findViewById(R.id.button_third_img);
        mButtonFourthImage = rootView.findViewById(R.id.button_fourth_img);
        mButtonFifthImage = rootView.findViewById(R.id.button_fifth_img);

        mButtonFirstImage.setOnClickListener(this);
        mButtonSecondImage.setOnClickListener(this);
        mButtonThirdImage.setOnClickListener(this);
        mButtonFourthImage.setOnClickListener(this);
        mButtonFifthImage.setOnClickListener(this);
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


    private void setCategoryAdapter() {
        mMakeRegionArrayList = getSpinnerArrayList(R.array.car_category_title, R.array.car_category_code);
        CustomSpinnerAdapter categorySpinnerAdapter = new CustomSpinnerAdapter(getActivity(), mMakeRegionArrayList);
        NothingSelectedSpinnerAdapter nothingSelectedSpinnerAdapter = new NothingSelectedSpinnerAdapter(categorySpinnerAdapter, R.layout.layout_spinner_hint, R.layout.layout_spinner_dropdown_hint, getActivity(), getString(R.string.select_region));
        mSpinnerCategory.setAdapter(nothingSelectedSpinnerAdapter);
        mSpinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    mMakeRegion = null;
                    return;
                }

                if (position > 0) {
                    position = position - 1;
                }
                SpinnerItem spinnerItem = mMakeRegionArrayList.get(position);
                mMakeRegion = spinnerItem.getCode();
                switch (mMakeRegion) {
                    case AppConstants.AMERICAN:
                        if (mMakeAmericanArrayList == null) {
                            mMakeAmericanArrayList = getSpinnerArrayList(R.array.car_sub_category_american_title, R.array.car_sub_category_american_code);
                        }
                        setSubCategoryAdapter(mMakeAmericanArrayList);
                        break;
                    case AppConstants.EUROPEAN:
                        if (mMakeEuropeanArrayList == null) {
                            mMakeEuropeanArrayList = getSpinnerArrayList(R.array.car_sub_category_european_title, R.array.car_sub_category_european_code);
                        }
                        setSubCategoryAdapter(mMakeEuropeanArrayList);
                        break;
                    case AppConstants.ASIAN:
                        if (mMakeAsianArrayList == null) {
                            mMakeAsianArrayList = getSpinnerArrayList(R.array.car_sub_category_asian_title, R.array.car_sub_category_asian_code);
                        }
                        setSubCategoryAdapter(mMakeAsianArrayList);
                        break;
                    case AppConstants.SCRAP:
                        if (mMakeScrapArrayList == null) {
                            mMakeScrapArrayList = getSpinnerArrayList(R.array.car_sub_category_scrap_title, R.array.car_sub_category_scrap_code);
                        }
                        setSubCategoryAdapter(mMakeScrapArrayList);
                        break;
                    case AppConstants.MARINE:
                        if (mMakeMarineArrayList == null) {
                            mMakeMarineArrayList = getSpinnerArrayList(R.array.car_sub_category_marine_title, R.array.car_sub_category_marine_code);
                        }
                        setSubCategoryAdapter(mMakeMarineArrayList);
                        break;
                    case AppConstants.ACCESSORIES:
                        if (mAccessoriesMarineArrayList == null) {
                            mAccessoriesMarineArrayList = getSpinnerArrayList(R.array.car_sub_category_accessories_title, R.array.car_sub_category_accessories_code);
                        }
                        setSubCategoryAdapter(mAccessoriesMarineArrayList);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setSubCategoryAdapter(ArrayList<SpinnerItem> spinnerArrayList) {


        CustomSpinnerAdapter categorySpinnerAdapter = new CustomSpinnerAdapter(getActivity(), spinnerArrayList);
        NothingSelectedSpinnerAdapter nothingSelectedSpinnerAdapter = new NothingSelectedSpinnerAdapter(categorySpinnerAdapter, R.layout.layout_spinner_hint, R.layout.layout_spinner_dropdown_hint, getActivity(), getString(R.string.select_model));
        mSpinnerSubCategory.setAdapter(nothingSelectedSpinnerAdapter);
        mSpinnerSubCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    mMake = null;
                    return;
                }

                if (position > 0) {
                    position = position - 1;
                }
                SpinnerItem spinnerItem = null;
                switch (mMakeRegion) {
                    case AppConstants.AMERICAN:
                        spinnerItem = mMakeAmericanArrayList.get(position);
                        break;
                    case AppConstants.EUROPEAN:
                        spinnerItem = mMakeEuropeanArrayList.get(position);
                        break;
                    case AppConstants.ASIAN:
                        spinnerItem = mMakeAsianArrayList.get(position);
                        break;
                    case AppConstants.SCRAP:
                        spinnerItem = mMakeScrapArrayList.get(position);
                        break;
                    case AppConstants.MARINE:
                        spinnerItem = mMakeMarineArrayList.get(position);
                        break;
                    case AppConstants.ACCESSORIES:
                        spinnerItem = mAccessoriesMarineArrayList.get(position);
                        break;
                    default:
                        spinnerItem = mMakeAmericanArrayList.get(position);
                        break;
                }
                mMake = spinnerItem.getCode();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        mSpinnerSubCategory.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (mMakeRegion == null) {
                            showSnackBar(getString(R.string.please_select_region_first));
                            return true;
                        }
                        break;
                }
                return false;
            }
        });
    }

    private ArrayList<SpinnerItem> getSpinnerArrayList(int subCategoryTitlesId, int subCategoryCodesId) {
        String[] subCategoryTitles = getResources().getStringArray(subCategoryTitlesId);
        String[] subCategoryCodes = getResources().getStringArray(subCategoryCodesId);
        ArrayList<SpinnerItem> spinnerItems = new ArrayList<>();
        for (int i = 0; i < subCategoryTitles.length; i++) {
            SpinnerItem spinnerItem = new SpinnerItem(subCategoryTitles[i], subCategoryCodes[i]);
            spinnerItems.add(spinnerItem);
        }
        return spinnerItems;
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

    private void openPhotoOptions() {
        Intent intent = new Intent(getActivity(), CameraGalleryActivity.class);
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case CHOOSE_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    mProfileImagePath = data.getStringExtra(AppConstants.EXTRA_IMAGE_PATH);
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

                    //mButtonLogin.setText(R.string.logout);
                }
                break;
            default:
                break;
        }
    }

    private void setAddImageTexVisibility(int imageIndex, int visibility) {
        switch (imageIndex) {
            case 1:
                mButtonFirstImage.findViewById(R.id.textview_add_image).setVisibility(visibility);
                break;
            case 2:
                mButtonSecondImage.findViewById(R.id.textview_add_image).setVisibility(visibility);
                break;
            case 3:
                mButtonThirdImage.findViewById(R.id.textview_add_image).setVisibility(visibility);
                break;
            case 4:
                mButtonFourthImage.findViewById(R.id.textview_add_image).setVisibility(visibility);
                break;
            case 5:
                mButtonFifthImage.findViewById(R.id.textview_add_image).setVisibility(visibility);
                break;
            default:
                break;
        }
    }

    private void setRemoveImageIconVisibility(int imageIndex, int visibility) {
        switch (imageIndex) {
            case 1:
                mButtonFirstImage.findViewById(R.id.imagebutton_remove).setVisibility(visibility);
                break;
            case 2:
                mButtonSecondImage.findViewById(R.id.imagebutton_remove).setVisibility(visibility);
                break;
            case 3:
                mButtonThirdImage.findViewById(R.id.imagebutton_remove).setVisibility(visibility);
                break;
            case 4:
                mButtonFourthImage.findViewById(R.id.imagebutton_remove).setVisibility(visibility);
                break;
            case 5:
                mButtonFifthImage.findViewById(R.id.imagebutton_remove).setVisibility(visibility);
                break;
            default:
                break;
        }
    }

    private void setRemoveImageIconTags() {
        mButtonFirstImage.findViewById(R.id.imagebutton_remove).setTag(AppConstants.REMOVE_IMAGE_ONE);
        mButtonSecondImage.findViewById(R.id.imagebutton_remove).setTag(AppConstants.REMOVE_IMAGE_TWO);
        mButtonThirdImage.findViewById(R.id.imagebutton_remove).setTag(AppConstants.REMOVE_IMAGE_THREE);
        mButtonFourthImage.findViewById(R.id.imagebutton_remove).setTag(AppConstants.REMOVE_IMAGE_FOUR);
        mButtonFifthImage.findViewById(R.id.imagebutton_remove).setTag(AppConstants.REMOVE_IMAGE_FIVE);
    }

    private void registerRemoveImageListeners() {
        mButtonFirstImage.findViewById(R.id.imagebutton_remove).setOnClickListener(mRemoveImageClickListener);
        mButtonSecondImage.findViewById(R.id.imagebutton_remove).setOnClickListener(mRemoveImageClickListener);
        mButtonThirdImage.findViewById(R.id.imagebutton_remove).setOnClickListener(mRemoveImageClickListener);
        mButtonFourthImage.findViewById(R.id.imagebutton_remove).setOnClickListener(mRemoveImageClickListener);
        mButtonFifthImage.findViewById(R.id.imagebutton_remove).setOnClickListener(mRemoveImageClickListener);
    }

    private View.OnClickListener mRemoveImageClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            removeImage((String) v.getTag());
        }
    };

    private void removeImage(String tag) {
        mImagesAdded--;
        switch (tag) {
            case AppConstants.REMOVE_IMAGE_ONE:
                ((ImageView) mButtonFirstImage.findViewById(R.id.imageview_ad)).setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_add_images));
                setRemoveImageIconVisibility(1, View.GONE);
                setAddImageTexVisibility(1, View.VISIBLE);
                mImagePaths[0] = null;
                break;
            case AppConstants.REMOVE_IMAGE_TWO:
                ((ImageView) mButtonSecondImage.findViewById(R.id.imageview_ad)).setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_add_images));
                setRemoveImageIconVisibility(2, View.GONE);
                setAddImageTexVisibility(2, View.VISIBLE);
                mImagePaths[1] = null;
                break;
            case AppConstants.REMOVE_IMAGE_THREE:
                ((ImageView) mButtonThirdImage.findViewById(R.id.imageview_ad)).setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_add_images));
                setRemoveImageIconVisibility(3, View.GONE);
                setAddImageTexVisibility(3, View.VISIBLE);
                mImagePaths[2] = null;
                break;
            case AppConstants.REMOVE_IMAGE_FOUR:
                ((ImageView) mButtonFourthImage.findViewById(R.id.imageview_ad)).setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_add_images));
                setRemoveImageIconVisibility(4, View.GONE);
                setAddImageTexVisibility(4, View.VISIBLE);
                mImagePaths[3] = null;
                break;
            case AppConstants.REMOVE_IMAGE_FIVE:
                ((ImageView) mButtonFifthImage.findViewById(R.id.imageview_ad)).setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_add_images));
                setRemoveImageIconVisibility(5, View.GONE);
                setAddImageTexVisibility(5, View.VISIBLE);
                mImagePaths[4] = null;
                break;
            default:
                break;
        }
    }

    private void removeAllImages() {
        removeImage(AppConstants.REMOVE_IMAGE_ONE);
        removeImage(AppConstants.REMOVE_IMAGE_TWO);
        removeImage(AppConstants.REMOVE_IMAGE_THREE);
        removeImage(AppConstants.REMOVE_IMAGE_FOUR);
        removeImage(AppConstants.REMOVE_IMAGE_FIVE);
    }

    private void resetEditTexts() {
        mEditTextTitle.setText("");
        mEditTextMobile.setText("");
        mEditTextPrice.setText("");
        mEditTextModel.setText("");
        mEditTextDescription.setText("");
    }

    private void resetAllFields() {
        removeAllImages();
        resetEditTexts();
        mMake = null;
        mMakeRegion = null;
        setCategoryAdapter();
        setSubCategoryAdapter(getSpinnerArrayList(R.array.car_sub_category_american_title, R.array.car_sub_category_american_code));
    }

    private class LongOperation extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            OKHTTpUplaod();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mProgressDialog.dismiss();
            super.onPostExecute(aVoid);
        }
    }

    private void OKHTTpUplaod() {

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

        String makeRegion = mMakeRegion;
        String make = mMake;
        String title = mEditTextTitle.getText().toString();
        String price = mEditTextPrice.getText().toString();
        String phone = mEditTextMobile.getText().toString();
        String model = mEditTextModel.getText().toString();
        String description = mEditTextDescription.getText().toString();
        MediaType MEDIA_TYPE_IMAGE = MediaType.parse("image/*");

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(AppConstants.UUID, PrefUtility.getAccessToken())
                .addFormDataPart(AppConstants.DEVICE_PHONE, PrefUtility.getAccessToken())
                .addFormDataPart(AppConstants.MAKE_REGION, makeRegion)
                .addFormDataPart(AppConstants.MAKE, make)
                .addFormDataPart(AppConstants.TITLE, title)
                .addFormDataPart(AppConstants.PRICE, price)
                .addFormDataPart(AppConstants.MODEL, model)
                .addFormDataPart(AppConstants.PHONE, phone)
                .addFormDataPart(AppConstants.DESCRIPTION, description);

        for (int i = 0; i < 5; i++) {
            if (mImagePaths[i] != null) {
                String attributeName = "image" + (i + 1);
                String imageName = "image" + (i + 1) + ".jpeg";
                builder.addFormDataPart(attributeName, imageName, RequestBody.create(MEDIA_TYPE_IMAGE, new File(mImagePaths[i])));
            }
        }
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(urlString)
                .post(requestBody)
                .build();

        okhttp3.Response response = null;
        try {
            response = client.newCall(request).execute();
            final String response_str = response.body().string();
            Logger.d(TAG, "Response from server : " + response_str);
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            } else {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response_str);
                    final String status = jsonObject.getString("status");
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                showSnackBar(response_str);
                                if (status.equals("success")) {
                                    resetAllFields();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showSnackBar(String message) {
        Utils.showSnackBar(getActivity().findViewById(R.id.coordinator_layout), message);
    }
}
