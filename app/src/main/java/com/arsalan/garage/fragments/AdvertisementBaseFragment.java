package com.arsalan.garage.fragments;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.app.Fragment;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.arsalan.garage.R;
import com.arsalan.garage.adapters.CustomSpinnerAdapter;
import com.arsalan.garage.adapters.NothingSelectedSpinnerAdapter;
import com.arsalan.garage.models.SpinnerItem;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.ImageUtils;
import com.arsalan.garage.utils.Logger;
import com.arsalan.garage.utils.PrefUtility;
import com.arsalan.garage.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * <p/>
 * Created by: Noor  Alam on 17/09/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */

public abstract class AdvertisementBaseFragment extends Fragment implements View.OnClickListener {

    protected static final String TAG = "PostAdTabFragment";
    protected Spinner mSpinnerCategory;
    protected Spinner mSpinnerSubCategory;
    protected static final int CHOOSE_PHOTO = 1000;
    protected String[] mImagePaths;
    protected EditText mEditTextTitle;
    protected EditText mEditTextMobile;
    protected EditText mEditTextPrice;
    protected EditText mEditTextModel;
    protected EditText mEditTextDescription;
    protected String mMakeRegion;
    protected String mMake;
    protected ArrayList<SpinnerItem> mMakeRegionArrayList;
    protected ArrayList<SpinnerItem> mMakeAmericanArrayList;
    protected ArrayList<SpinnerItem> mMakeEuropeanArrayList;
    protected ArrayList<SpinnerItem> mMakeAsianArrayList;
    protected ArrayList<SpinnerItem> mMakeScrapArrayList;
    protected ArrayList<SpinnerItem> mMakeMarineArrayList;
    protected ArrayList<SpinnerItem> mMakeAccessoriesArrayList;

    protected LinearLayout mLinearLayoutAddViewContainer;
    protected View mButtonFirstImage;
    protected View mButtonSecondImage;
    protected View mButtonThirdImage;
    protected View mButtonFourthImage;
    protected View mButtonFifthImage;
    protected int MAX_ADV = 5;
    protected int mImagesAdded = 0;
    protected ProgressDialog mProgressDialog;
    protected String mId;
    protected ArrayList<String> mImageList;
    protected String POST_TYPE_NEW = "post_type_new";
    protected String POST_TYPE_UPDATE = "post_type_update";

    protected void init(View rootView) {

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

    }

    protected void setRemoveImageIconTags() {
        mButtonFirstImage.findViewById(R.id.imagebutton_remove).setTag(AppConstants.REMOVE_IMAGE_ONE);
        mButtonSecondImage.findViewById(R.id.imagebutton_remove).setTag(AppConstants.REMOVE_IMAGE_TWO);
        mButtonThirdImage.findViewById(R.id.imagebutton_remove).setTag(AppConstants.REMOVE_IMAGE_THREE);
        mButtonFourthImage.findViewById(R.id.imagebutton_remove).setTag(AppConstants.REMOVE_IMAGE_FOUR);
        mButtonFifthImage.findViewById(R.id.imagebutton_remove).setTag(AppConstants.REMOVE_IMAGE_FIVE);
    }

    protected void registerRemoveImageListeners() {
        mButtonFirstImage.findViewById(R.id.imagebutton_remove).setOnClickListener(mRemoveImageClickListener);
        mButtonSecondImage.findViewById(R.id.imagebutton_remove).setOnClickListener(mRemoveImageClickListener);
        mButtonThirdImage.findViewById(R.id.imagebutton_remove).setOnClickListener(mRemoveImageClickListener);
        mButtonFourthImage.findViewById(R.id.imagebutton_remove).setOnClickListener(mRemoveImageClickListener);
        mButtonFifthImage.findViewById(R.id.imagebutton_remove).setOnClickListener(mRemoveImageClickListener);
    }

    protected View.OnClickListener mRemoveImageClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            removeImage((String) v.getTag());
        }
    };

    protected void removeImage(String tag) {
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

    protected void setAddImageTexVisibility(int imageIndex, int visibility) {
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


    protected void setRemoveImageIconVisibility(int imageIndex, int visibility) {
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    protected void setSubCategoryAdapter(ArrayList<SpinnerItem> spinnerArrayList) {

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
                        spinnerItem = mMakeAccessoriesArrayList.get(position);
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

    protected ArrayList<SpinnerItem> getSpinnerArrayList(int subCategoryTitlesId, int subCategoryCodesId) {
        String[] subCategoryTitles = getResources().getStringArray(subCategoryTitlesId);
        String[] subCategoryCodes = getResources().getStringArray(subCategoryCodesId);
        ArrayList<SpinnerItem> spinnerItems = new ArrayList<>();
        for (int i = 0; i < subCategoryTitles.length; i++) {
            SpinnerItem spinnerItem = new SpinnerItem(subCategoryTitles[i], subCategoryCodes[i]);
            spinnerItems.add(spinnerItem);
        }
        return spinnerItems;
    }


    protected void removeAllImages() {
        removeImage(AppConstants.REMOVE_IMAGE_ONE);
        removeImage(AppConstants.REMOVE_IMAGE_TWO);
        removeImage(AppConstants.REMOVE_IMAGE_THREE);
        removeImage(AppConstants.REMOVE_IMAGE_FOUR);
        removeImage(AppConstants.REMOVE_IMAGE_FIVE);
    }

    protected void resetEditTexts() {
        mEditTextTitle.setText("");
        mEditTextMobile.setText("");
        mEditTextPrice.setText("");
        mEditTextModel.setText("");
        mEditTextDescription.setText("");
    }

    protected void resetAllFields() {
        removeAllImages();
        resetEditTexts();
        mMake = null;
        mMakeRegion = null;
        setCategoryAdapter();
        setSubCategoryAdapter(getSpinnerArrayList(R.array.car_sub_category_american_title, R.array.car_sub_category_american_code));
    }

    protected class LongOperation extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            OKHTTpUplaod(mImagePaths, getUploadUrl());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }
            super.onPostExecute(aVoid);
        }
    }

    private MultipartBody.Builder setFormData(MultipartBody.Builder builder, Map<String, Object> formData) {
        for (Map.Entry<String, Object> entry : formData.entrySet()) {
            String key = entry.getKey();
            String value = (String) entry.getValue();
            builder.addFormDataPart(key, value);
        }
        return builder;
    }

    private Map<String, Object> getFormData() {

        Map<String, Object> formValues = new HashMap<>();

        String accessToken = PrefUtility.getAccessToken();
        String makeRegion = mMakeRegion;
        String make = mMake;
        if (mMakeRegion.equalsIgnoreCase(AppConstants.MARINE) || mMakeRegion.equalsIgnoreCase(AppConstants.SCRAP) || mMakeRegion.equalsIgnoreCase(AppConstants.ACCESSORIES)) {
            makeRegion = make;
        }
        String title = mEditTextTitle.getText().toString();
        String price = mEditTextPrice.getText().toString();
        String phone = mEditTextMobile.getText().toString();
        String model = mEditTextModel.getText().toString();
        String description = mEditTextDescription.getText().toString();
        String id = null;

        formValues.put(AppConstants.UUID, accessToken);
        formValues.put(AppConstants.DEVICE_PHONE, accessToken);
        formValues.put(AppConstants.MAKE_REGION, makeRegion);
        formValues.put(AppConstants.MAKE, make);
        formValues.put(AppConstants.TITLE, title);
        formValues.put(AppConstants.PRICE, price);
        formValues.put(AppConstants.MODEL, model);
        formValues.put(AppConstants.PHONE, phone);
        formValues.put(AppConstants.DESCRIPTION, description);
        if (getPostType().equalsIgnoreCase(POST_TYPE_UPDATE)) {
            id = getArguments().getString(AppConstants.ID);
            formValues.put(AppConstants.ID, id);
        }
        return formValues;
    }

    private byte[] getCompressedImage(Bitmap bitmap, int imageQuality){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, imageQuality, stream);
        return stream.toByteArray();
    }

    private void setImageData(MultipartBody.Builder builder, String[] imagePaths){
        MediaType MEDIA_TYPE_IMAGE = MediaType.parse("image/*");
        for (int i = 0; i < imagePaths.length; i++) {
            if (imagePaths[i] != null) {
                String attributeName = "image" + (i + 1);
                String imageName = "image" + (i + 1) + ".jpeg";

                Bitmap bitmap = ImageUtils.getSampledBitmapFromFilePath(imagePaths[i], AppConstants.UPLOAD_IMAGE_WIDTH, AppConstants.UPLOAD_IMAGE_HEIGHT /* aspect 9:16 */);
                byte[] byteArray = getCompressedImage(bitmap, AppConstants.UPLOAD_IMAGE_QUALITY);
                //builder.addFormDataPart(attributeName, imageName, RequestBody.create(MEDIA_TYPE_IMAGE, new File(mImagePaths[i])));
                builder.addFormDataPart(attributeName, imageName, RequestBody.create(MEDIA_TYPE_IMAGE, byteArray));
            }
        }
    }

    private void OKHTTpUplaod(String[] imagePaths, String uploadUrl) {

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        Map formData = getFormData();
        setFormData(builder, formData);
        setImageData(builder, imagePaths);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(uploadUrl)
                .post(requestBody)
                .build();

        okhttp3.Response response = null;
        try {
            response = client.newCall(request).execute();
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
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
                                    finishedUploading();
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


    protected void showSnackBar(String message) {
        Utils.showSnackBar(getActivity().findViewById(R.id.coordinator_layout), message);
    }

    @Override
    public void onClick(View v) {

    }

    abstract String getUploadUrl();

    abstract void finishedUploading();

    abstract String getPostType();
}
