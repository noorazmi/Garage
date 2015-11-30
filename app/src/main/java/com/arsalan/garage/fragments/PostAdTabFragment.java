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
import android.widget.Spinner;
import android.widget.Toast;

import com.arsalan.garage.R;
import com.arsalan.garage.activities.CameraGalleryActivity;
import com.arsalan.garage.adapters.SpinnerAdapter;
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
import java.util.List;

public class PostAdTabFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "PostAdTabFragment";
    private Spinner mSpinnerCategory;
    private Spinner mSpinnerSubCategory;
    private static final int CHOOSE_PHOTO = 1000;
    private String mProfileImagePath = "";
    private int mCurrentImageSelection = 0;
    private List<String> imagePaths;
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


    public PostAdTabFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMakeAmericanArrayList = getSpinnerArrayList(R.array.car_sub_category_american_title, R.array.car_sub_category_american_code);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_post_ad_tab, container, false);
        mSpinnerCategory = (Spinner) rootView.findViewById(R.id.spinner_category);
        mSpinnerSubCategory = (Spinner) rootView.findViewById(R.id.spinner_sub_category);
        rootView.findViewById(R.id.button_post_add).setOnClickListener(this);
        mEditTextTitle = (EditText)rootView.findViewById(R.id.edittext_title);
        mEditTextMobile = (EditText)rootView.findViewById(R.id.edittext_mobile_no);
        mEditTextPrice = (EditText)rootView.findViewById(R.id.edittext_price);
        mEditTextDescription = (EditText)rootView.findViewById(R.id.edittext_description);
        imagePaths = new ArrayList<>();
        setCategoryAdapter();
        setSubCategoryAdapter(getSpinnerArrayList(R.array.car_sub_category_american_title, R.array.car_sub_category_american_code));
        rootView.findViewById(R.id.imageview_photo1).setOnClickListener(this);
        rootView.findViewById(R.id.imageview_photo2).setOnClickListener(this);
        rootView.findViewById(R.id.imageview_photo3).setOnClickListener(this);
        return rootView;
    }

    private void setCategoryAdapter() {
        mMakeRegionArrayList = getSpinnerArrayList(R.array.car_category_title, R.array.car_category_code);
        SpinnerAdapter categorySpinnerAdapter = new SpinnerAdapter(getActivity(), mMakeRegionArrayList);
        mSpinnerCategory.setAdapter(categorySpinnerAdapter);
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
        SpinnerAdapter categorySpinnerAdapter = new SpinnerAdapter(getActivity(), spinnerArrayList);
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
            case R.id.imageview_photo1:
                mCurrentImageSelection = 1;
                openPhotoOptions();
                break;
            case R.id.imageview_photo2:
                mCurrentImageSelection = 2;
                openPhotoOptions();
                break;
            case R.id.imageview_photo3:
                mCurrentImageSelection = 3;
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
                    imagePaths.add(mProfileImagePath);
                    if (mProfileImagePath != null) {
                        Bitmap bitmap = Utils.getBitmapFromPath(mProfileImagePath);
                        switch (mCurrentImageSelection) {
                            case 1:
                                ((ImageView) getView().findViewById(R.id.imageview_photo1)).setImageBitmap(bitmap);
                                break;
                            case 2:
                                ((ImageView) getView().findViewById(R.id.imageview_photo2)).setImageBitmap(bitmap);
                                break;
                            case 3:
                                ((ImageView) getView().findViewById(R.id.imageview_photo3)).setImageBitmap(bitmap);
                                break;
                            default:
                                break;
                        }

                    } else {
                        Utils.showToastMessage(getActivity(), "Please choose another image.");
                    }
                }
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

        File file1 = new File(imagePaths.get(0));
        File file2 = new File(imagePaths.get(1));
        File file3 = new File(imagePaths.get(2));
        String urlString = Urls.FORESALE_UPLOAD;
        HttpEntity resEntity = null;
        try {

            HttpParams httpParams = new BasicHttpParams();
            //httpParams.setParameter(AppConstants.DEVICE_PHONE, "32415263");

            HttpClient client = new DefaultHttpClient(httpParams);

            HttpPost post = new HttpPost(urlString);

            post.setParams(httpParams);
            FileBody bin1 = new FileBody(file1);
            FileBody bin2 = new FileBody(file2);
            FileBody bin3 = new FileBody(file3);
            MultipartEntity reqEntity = new MultipartEntity();
            reqEntity.addPart("image1", bin1);
            reqEntity.addPart("image2", bin2);
            reqEntity.addPart("image3", bin3);
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
