package com.arsalan.garage.fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.arsalan.garage.R;
import com.arsalan.garage.activities.CameraGalleryActivity;
import com.arsalan.garage.adapters.CustomSpinnerAdapter;
import com.arsalan.garage.adapters.NothingSelectedSpinnerAdapter;
import com.arsalan.garage.models.SpinnerItem;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.Logger;
import com.arsalan.garage.utils.Urls;
import com.arsalan.garage.utils.Utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.util.ArrayList;

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
    private EditText mEditTextDescription;
    private String mMakeRegion = AppConstants.AMERICAN;
    private String mMake;
    private ArrayList<SpinnerItem> mMakeRegionArrayList;
    private ArrayList<SpinnerItem> mMakeAmericanArrayList;
    private ArrayList<SpinnerItem> mMakeEuropeanArrayList;
    private ArrayList<SpinnerItem> mMakeAsianArrayList;
    private ProgressDialog mProgressDialog;
    private LinearLayout mLinearLayoutAddViewContainer;
    private View mButtonFirstImage;
    private View mButtonSecondImage;
    private View mButtonThirdImage;
    private View mButtonFourthImage;
    private View mButtonFifthImage;
    private int MAX_ADV = 5;


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
        mLinearLayoutAddViewContainer = (LinearLayout) rootView.findViewById(R.id.linear_layout_add_view_container);
        mSpinnerCategory = (Spinner) rootView.findViewById(R.id.spinner_category);
        mSpinnerSubCategory = (Spinner) rootView.findViewById(R.id.spinner_sub_category);
        rootView.findViewById(R.id.button_post_add).setOnClickListener(this);
        mEditTextTitle = (EditText)rootView.findViewById(R.id.edittext_title);
        mEditTextMobile = (EditText)rootView.findViewById(R.id.edittext_mobile_no);
        mEditTextPrice = (EditText)rootView.findViewById(R.id.edittext_price);
        mEditTextDescription = (EditText)rootView.findViewById(R.id.edittext_description);
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
        setCategoryAdapter();
        setSubCategoryAdapter(getSpinnerArrayList(R.array.car_sub_category_american_title, R.array.car_sub_category_american_code));
        return rootView;
    }

    private void setCategoryAdapter() {
        mMakeRegionArrayList = getSpinnerArrayList(R.array.car_category_title, R.array.car_category_code);



        CustomSpinnerAdapter categorySpinnerAdapter = new CustomSpinnerAdapter(getActivity(), mMakeRegionArrayList);
        NothingSelectedSpinnerAdapter nothingSelectedSpinnerAdapter = new NothingSelectedSpinnerAdapter(categorySpinnerAdapter, R.layout.layout_spinner_item_eng, R.layout.layout_spinner_item_eng, getActivity(), getString(R.string.select_region));
        mSpinnerCategory.setAdapter(nothingSelectedSpinnerAdapter);
        mSpinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerItem spinnerItem = mMakeRegionArrayList.get(position);
                mMakeRegion = spinnerItem.getCode();
                switch (mMakeRegion) {
                    case AppConstants.AMERICAN:
                        if(mMakeAmericanArrayList == null){
                            mMakeAmericanArrayList = getSpinnerArrayList(R.array.car_sub_category_american_title, R.array.car_sub_category_american_code);
                        }
                        setSubCategoryAdapter(mMakeAmericanArrayList);
                        break;
                    case AppConstants.EUROPEAN:
                        if(mMakeEuropeanArrayList == null){
                            mMakeEuropeanArrayList = getSpinnerArrayList(R.array.car_sub_category_european_title, R.array.car_sub_category_european_code);
                        }
                        setSubCategoryAdapter(mMakeEuropeanArrayList);
                        break;
                    case AppConstants.ASIAN:
                        if(mMakeAsianArrayList == null){
                            mMakeAsianArrayList = getSpinnerArrayList(R.array.car_sub_category_asian_title, R.array.car_sub_category_asian_code);
                        }
                        setSubCategoryAdapter(mMakeAsianArrayList);
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
        mSpinnerSubCategory.setAdapter(categorySpinnerAdapter);
        mSpinnerSubCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerItem spinnerItem = null;
                switch (mMakeRegion){
                    case AppConstants.AMERICAN:
                        spinnerItem = mMakeAmericanArrayList.get(position);
                    break;
                    case AppConstants.EUROPEAN:
                        spinnerItem = mMakeEuropeanArrayList.get(position);
                    break;
                    case AppConstants.ASIAN:
                        spinnerItem = mMakeAsianArrayList.get(position);
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
                mProgressDialog = new ProgressDialog(getActivity());
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
                    mImagePaths[mCurrentImageSelection-1] = mProfileImagePath;
                    if (mProfileImagePath != null) {
                        Bitmap bitmap = Utils.getBitmapFromPath(mProfileImagePath);
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
            default:
                break;
        }
    }

    private void setAddImageTexVisibility(int imageIndex, int visibility){
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

    private void setRemoveImageIconVisibility(int imageIndex, int visibility){
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


    private class LongOperation extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            doFileUpload();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mProgressDialog.dismiss();
            super.onPostExecute(aVoid);
        }
    }


    private void doFileUpload() {

        //File file1 = new File(mImagePaths[0]);
        //File file2 = new File(mImagePaths[1]);
        //File file3 = new File(mImagePaths[2]);
        //File file4 = new File(mImagePaths[3]);
        //File file5 = new File(mImagePaths[4]);

        String urlString = Urls.FORESALE_UPLOAD;
        HttpEntity resEntity = null;
        try {

            HttpParams httpParams = new BasicHttpParams();
            //httpParams.setParameter(AppConstants.DEVICE_PHONE, "32415263");

            HttpClient client = new DefaultHttpClient(httpParams);

            HttpPost post = new HttpPost(urlString);

            post.setParams(httpParams);
            MultipartEntity reqEntity = new MultipartEntity();

            for (int i = 0; i < 5 ; i++) {
                if(mImagePaths[i] != null){
                    reqEntity.addPart("image"+(i+1), new FileBody(new File(mImagePaths[i])));
                }
            }

            //FileBody bin1 = new FileBody(file1);
            //FileBody bin2 = new FileBody(file2);
            //FileBody bin3 = new FileBody(file3);
            //reqEntity.addPart("image1", bin1);
            //reqEntity.addPart("image2", bin2);
            //reqEntity.addPart("image3", bin3);

            reqEntity.addPart(AppConstants.DEVICE_PHONE, new StringBody(Utils.getUDID(getActivity())));
            reqEntity.addPart(AppConstants.MAKE_REGION, new StringBody(mMakeRegion));
            reqEntity.addPart(AppConstants.MAKE, new StringBody(mMake));
            reqEntity.addPart(AppConstants.TITLE, new StringBody(mEditTextTitle.getText().toString().trim()));
            reqEntity.addPart(AppConstants.PHONE, new StringBody(mEditTextMobile.getText().toString().trim()));
            reqEntity.addPart(AppConstants.PRICE, new StringBody(mEditTextPrice.getText().toString().trim()));
            reqEntity.addPart(AppConstants.DESCRIPTION, new StringBody(mEditTextDescription.getText().toString().trim()));
            post.setEntity(reqEntity);
            HttpResponse response = client.execute(post);
            resEntity = response.getEntity();
            final String response_str = EntityUtils.toString(resEntity);
            if (resEntity != null) {
                Log.i("RESPONSE", response_str);
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        try {
                            Logger.d(TAG, "n Response from server : n " + response_str);
                            Toast.makeText(getActivity(), "Server response:" + response_str, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        } catch (Exception ex) {
            Log.e("Debug", "error: " + ex.getMessage(), ex);
        }
    }

}
