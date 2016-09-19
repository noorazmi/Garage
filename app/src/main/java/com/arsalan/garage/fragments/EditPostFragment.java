package com.arsalan.garage.fragments;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.arsalan.garage.R;
import com.arsalan.garage.activities.CameraGalleryActivity;
import com.arsalan.garage.adapters.CustomSpinnerAdapter;
import com.arsalan.garage.adapters.NothingSelectedSpinnerAdapter;
import com.arsalan.garage.models.SpinnerItem;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.Logger;
import com.arsalan.garage.utils.Urls;
import com.arsalan.garage.utils.Utils;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * <p/>
 * Created by: Noor  Alam on 17/09/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */

public class EditPostFragment extends AdvertisementBaseFragment implements View.OnClickListener {

    private int mCurrentImageSelection = 0;
    DownloadManager mDownloadManager;
    private int mImageDownloadCounter = -1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImagePaths = new String[MAX_ADV];
        mMakeAmericanArrayList = getSpinnerArrayList(R.array.car_sub_category_american_title, R.array.car_sub_category_american_code);
        mDownloadManager = (DownloadManager) getActivity().getSystemService(DOWNLOAD_SERVICE);
        getActivity().getApplicationContext().registerReceiver(mImageDownloadReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    public void onResume() {
        super.onResume();
        populateData();
    }

    private void showImages() {
        for (int i = 0; i < mImageList.size(); i++) {
            String url = mImageList.get(i);
            downloadImages(url, "image" + i);
        }
    }


    private void downloadImages(String url, String image) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle(image);
        request.setDestinationInExternalFilesDir(getActivity(), "garage", image + ".jpg");
        mDownloadManager.enqueue(request);
    }


    private BroadcastReceiver mImageDownloadReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            Bundle extras = intent.getExtras();
            DownloadManager.Query q = new DownloadManager.Query();
            q.setFilterById(extras.getLong(DownloadManager.EXTRA_DOWNLOAD_ID));
            Cursor c = mDownloadManager.query(q);

            if (c.moveToFirst()) {
                int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
                if (status == DownloadManager.STATUS_SUCCESSFUL) {

                    if(mImageList.size() - 1 == mImageDownloadCounter){
                        unregisterReceiver();
                        return;
                    }
                    String filePath = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
                    mImageDownloadCounter++;
                    mCurrentImageSelection++;
                    mImagePaths[mImageDownloadCounter] = filePath;
                    showImageAndVisibility(filePath);
                }
            }
            c.close();
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit_post, container, false);
        init(rootView);
        return rootView;
    }

    private void populateData() {
        Bundle bundle = getArguments();
        mEditTextTitle.setText(bundle.getString(AppConstants.TITLE));
        mEditTextMobile.setText(bundle.getString(AppConstants.PHONE));
        mEditTextPrice.setText(bundle.getString(AppConstants.PRICE));
        mEditTextModel.setText(bundle.getString(AppConstants.MODEL));
        mEditTextDescription.setText(bundle.getString(AppConstants.DESCRIPTION));
        mMake = bundle.getString(AppConstants.MODEL);
        mId = bundle.getString(AppConstants.ID);
        mImageList = bundle.getStringArrayList(AppConstants.IMAGE_LIST);
        for (int i = 0; i < mImageList.size(); i++) {
            Logger.e("TAG", "ImagesCame****:" + mImageList.get(i));
        }
        showImages();
        setCategoryAdapter();
        setCategorySelection(bundle.getString(AppConstants.CATEGORY));
    }


    private void setSubCategorySelection(String subCategory) {
        for (int i = 1; i < mSpinnerSubCategory.getAdapter().getCount(); i++) {
            if (((SpinnerItem) mSpinnerSubCategory.getAdapter().getItem(i)).getCode().toLowerCase().contains(subCategory.toLowerCase())) {
                mSpinnerSubCategory.setSelection(i);
                return;
            }
        }
    }

    private void setCategorySelection(String category) {
        switch (category.toLowerCase()) {
            case AppConstants.AMERICAN:
                mSpinnerCategory.setSelection(1);
                break;
            case AppConstants.EUROPEAN:
                mSpinnerCategory.setSelection(2);
                break;
            case AppConstants.ASIAN:
                mSpinnerCategory.setSelection(3);
                break;
            case AppConstants.SCRAP:
                mSpinnerCategory.setSelection(4);
                break;
            case AppConstants.MARINE:
                mSpinnerCategory.setSelection(5);
                break;
            case AppConstants.ACCESSORIES:
                mSpinnerCategory.setSelection(6);
                break;
        }
    }

    protected void setCategoryAdapter() {
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
                        if (mMakeAccessoriesArrayList == null) {
                            mMakeAccessoriesArrayList = getSpinnerArrayList(R.array.car_sub_category_accessories_title, R.array.car_sub_category_accessories_code);
                        }
                        setSubCategoryAdapter(mMakeAccessoriesArrayList);
                        break;
                    default:
                        break;
                }

                setSubCategorySelection(getArguments().getString(AppConstants.SUB_CATEGORY));
            }

            //
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
        String urlString = Urls.FORESALE_UPDATE;
        if (mMakeRegion.equalsIgnoreCase(AppConstants.SCRAP)) {
            urlString = Urls.SCRAP_UPDATE;
        }

        if (mMakeRegion.equalsIgnoreCase(AppConstants.MARINE)) {
            urlString = Urls.MARINE_UPDATE;
        }

        if (mMakeRegion.equalsIgnoreCase(AppConstants.ACCESSORIES)) {
            urlString = Urls.ACCESSORIES_UPDATE;
        }
        return urlString;
    }

    @Override
    void finishedUploading() {
        Utils.showToastMessage(getActivity(), "Post successfully updated.");
        getActivity().finish();
    }

    @Override
    String getPostType() {
        return POST_TYPE_UPDATE;
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
                    String imagePath = data.getStringExtra(AppConstants.EXTRA_IMAGE_PATH);
                    showImageAndVisibility(imagePath);
                }
            default:
                break;
        }
    }


    private void showImageAndVisibility(String imagePath) {

        mImagePaths[mCurrentImageSelection - 1] = imagePath;
        if (imagePath != null) {
            Bitmap bitmap = Utils.getBitmapFromPath(imagePath);
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

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver();
    }

    private void unregisterReceiver(){
        if (mImageDownloadReceiver != null) {
            try {
                getActivity().unregisterReceiver(mImageDownloadReceiver);
            }catch (IllegalArgumentException e){
                e.printStackTrace();
            }
        }
    }
}
