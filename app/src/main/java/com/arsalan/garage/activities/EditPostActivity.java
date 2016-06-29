package com.arsalan.garage.activities;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
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
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class EditPostActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "PostAdTabFragment";
    private Spinner mSpinnerCategory;
    private Spinner mSpinnerSubCategory;
    private static final int CHOOSE_PHOTO = 1000;
    private int mCurrentImageSelection = 0;
    private String[] mImagePaths;
    private EditText mEditTextTitle;
    private EditText mEditTextMobile;
    private EditText mEditTextPrice;
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
    private String mId;
    private ArrayList<String> mImageList;
    private DownloadManager mDownloadManager;
    //private CustomProgressDialog mCustomProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setCustomTitleEnglish("Edit Post" , toolbar);
        mImagePaths = new String[MAX_ADV];
        mMakeAmericanArrayList = getSpinnerArrayList(R.array.car_sub_category_american_title, R.array.car_sub_category_american_code);
        init();
        populateData();

    }


    private void init() {
        mDownloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        registerReceiver(mImageDownloadReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        mLinearLayoutAddViewContainer = (LinearLayout) findViewById(R.id.linear_layout_add_view_container);
        mSpinnerCategory = (Spinner) findViewById(R.id.spinner_category);
        mSpinnerSubCategory = (Spinner) findViewById(R.id.spinner_sub_category);
        findViewById(R.id.button_post_add).setOnClickListener(this);
        mEditTextTitle = (EditText) findViewById(R.id.edittext_title);
        mEditTextMobile = (EditText) findViewById(R.id.edittext_mobile_no);
        mEditTextPrice = (EditText) findViewById(R.id.edittext_price);
        mEditTextDescription = (EditText) findViewById(R.id.edittext_description);
        mButtonFirstImage = findViewById(R.id.button_first_img);
        mButtonSecondImage = findViewById(R.id.button_second_img);
        mButtonThirdImage = findViewById(R.id.button_third_img);
        mButtonFourthImage = findViewById(R.id.button_fourth_img);
        mButtonFifthImage = findViewById(R.id.button_fifth_img);

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

    private void populateData() {
        Bundle bundle = getIntent().getExtras();
        mEditTextTitle.setText(bundle.getString(AppConstants.TITLE));
        mEditTextMobile.setText(bundle.getString(AppConstants.PHONE));
        mEditTextPrice.setText(bundle.getString(AppConstants.PRICE));
        mEditTextDescription.setText(bundle.getString(AppConstants.DESCRIPTION));
        mMake = bundle.getString(AppConstants.MODEL);
        mId = bundle.getString(AppConstants.ID);
        mImageList = bundle.getStringArrayList(AppConstants.IMAGE_LIST);
        for (int i = 0; i < mImageList.size() ; i++) {
            Logger.e("TAG", "ImagesCame****:"+mImageList.get(i));
        }
        showImages();
        setCategorySelection(bundle.getString(AppConstants.CATEGORY));
    }


    private void setSubCategorySelection(String  subCategory){
        for (int i = 1; i < mSpinnerSubCategory.getAdapter().getCount(); i++) {
            if(((SpinnerItem)mSpinnerSubCategory.getAdapter().getItem(i)).getCode().toLowerCase().contains(subCategory.toLowerCase())){
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
        }
    }

    private void showImages() {
        for (int i = 0; i < mImageList.size(); i++) {
            String url = mImageList.get(i);
            downloadImages(url, "image"+i);
        }
    }


    private void downloadImages(String url, String image){
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle(image);
        request.setDestinationInExternalFilesDir(this, "garage", image+".jpg");
        mDownloadManager.enqueue(request);
    }

    private int mImageDownloadCounter = -1;
    private BroadcastReceiver mImageDownloadReceiver = new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {

            Bundle extras = intent.getExtras();
            DownloadManager.Query q = new DownloadManager.Query();
            q.setFilterById(extras.getLong(DownloadManager.EXTRA_DOWNLOAD_ID));
            Cursor c = mDownloadManager.query(q);

            if (c.moveToFirst()) {
                int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
                if (status == DownloadManager.STATUS_SUCCESSFUL) {
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

    private void setCategoryAdapter() {
        mMakeRegionArrayList = getSpinnerArrayList(R.array.car_category_title, R.array.car_category_code);
        CustomSpinnerAdapter categorySpinnerAdapter = new CustomSpinnerAdapter(this, mMakeRegionArrayList);
        NothingSelectedSpinnerAdapter nothingSelectedSpinnerAdapter = new NothingSelectedSpinnerAdapter(categorySpinnerAdapter, R.layout.layout_spinner_hint, R.layout.layout_spinner_dropdown_hint, this, getString(R.string.select_region));
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
                    default:
                        break;
                }

                setSubCategorySelection(getIntent().getExtras().getString(AppConstants.SUB_CATEGORY));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setSubCategoryAdapter(ArrayList<SpinnerItem> spinnerArrayList) {


        CustomSpinnerAdapter categorySpinnerAdapter = new CustomSpinnerAdapter(this, spinnerArrayList);
        NothingSelectedSpinnerAdapter nothingSelectedSpinnerAdapter = new NothingSelectedSpinnerAdapter(categorySpinnerAdapter, R.layout.layout_spinner_hint, R.layout.layout_spinner_dropdown_hint, this, getString(R.string.select_model));
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
                if (mImagesAdded == 0) {
                    showSnackBar(getString(R.string.there_is_no_image_to_post));
                    return;
                }

                if (TextUtils.isEmpty(mMakeRegion) || TextUtils.isEmpty(mMake) || TextUtils.isEmpty(mEditTextTitle.getText().toString()) || TextUtils.isEmpty(mEditTextMobile.getText().toString()) || TextUtils.isEmpty(mEditTextPrice.getText().toString()) || TextUtils.isEmpty(mEditTextDescription.getText().toString())) {
                    showSnackBar(getString(R.string.all_fields_are_compulsory));
                    return;
                }
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
                new LongOperation().execute();
                break;
            default:
                break;
        }
    }

    private void openPhotoOptions() {
        Intent intent = new Intent(this, CameraGalleryActivity.class);
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


    private void showImageAndVisibility(String imagePath){

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
            Utils.showToastMessage(this, "Please choose another image.");
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

        for (int i = 0; i < 5 ; i++) {
            Logger.e("TAG", "ImagesUpload****:"+mImagePaths[i]);
        }


        String urlString = Urls.FORESALE_UPLOAD;
        if (mMakeRegion.equals(AppConstants.SCRAP)) {
            urlString = Urls.SCRAP_UPLOAD;
        }else if (mMakeRegion.equals(AppConstants.MARINE)){
            urlString = Urls.MARINE_UPLOAD;
        }


        HttpEntity resEntity = null;
        try {

            HttpParams httpParams = new BasicHttpParams();

            HttpClient client = new DefaultHttpClient(httpParams);

            HttpPost post = new HttpPost(urlString);

            post.setParams(httpParams);
            MultipartEntity reqEntity = new MultipartEntity();

            for (int i = 0; i < 5; i++) {
                if (mImagePaths[i] != null) {
                    reqEntity.addPart("image" + (i + 1), new FileBody(new File(mImagePaths[i])));
                }
            }

            reqEntity.addPart(AppConstants.DEVICE_PHONE, new StringBody(Utils.getUDID(this)));
            reqEntity.addPart(AppConstants.UUID, new StringBody(Utils.getUDID(this)));
            if (urlString.equals(Urls.SCRAP_UPLOAD)) {
                reqEntity.addPart(AppConstants.MAKE_REGION, new StringBody(mMake));
            } else {
                reqEntity.addPart(AppConstants.MAKE_REGION, new StringBody(mMakeRegion));
            }
            reqEntity.addPart(AppConstants.MAKE, new StringBody(mMake));
            reqEntity.addPart(AppConstants.MODEL, new StringBody(mMake));
            reqEntity.addPart(AppConstants.TITLE, new StringBody(mEditTextTitle.getText().toString().trim()));
            reqEntity.addPart(AppConstants.PHONE, new StringBody(mEditTextMobile.getText().toString().trim()));
            reqEntity.addPart(AppConstants.PRICE, new StringBody(mEditTextPrice.getText().toString().trim()));
            reqEntity.addPart(AppConstants.DESCRIPTION, new StringBody(mEditTextDescription.getText().toString().trim()));


            post.setEntity(reqEntity);
            HttpResponse response = client.execute(post);
            resEntity = response.getEntity();
            final String response_str = EntityUtils.toString(resEntity);
            JSONObject jsonObject = new JSONObject(response_str);
            final String status = jsonObject.getString("status");
            if (resEntity != null) {
                Log.i("RESPONSE", response_str);
                this.runOnUiThread(new Runnable() {
                    public void run() {
                        try {
                            Logger.d(TAG, "Response from server : " + response_str);
                            showSnackBar(response_str);
                            if(status.equals("success")){
                                resetAllFields();
                            }
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

    private void showSnackBar(String message) {
        Utils.showSnackBar(this.findViewById(R.id.coordinator_layout), message);
    }

}
