package com.arsalan.garage.fragments;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.arsalan.garage.R;
import com.arsalan.garage.activities.CameraGalleryActivity;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.Utils;

public class PostAdTabFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "PostAdTabFragment";
    private Spinner mSpinnerCategory;
    private Spinner mSpinnerSubCategory;
    private static final int CHOOSE_PHOTO = 1000;
    private String mProfileImagePath = "";
    private int mCurrentImageSelection = 0;

    public PostAdTabFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_post_ad_tab, container, false);
        mSpinnerCategory = (Spinner) rootView.findViewById(R.id.spinner_category);
        mSpinnerSubCategory = (Spinner) rootView.findViewById(R.id.spinner_sub_category);
        setCategoryAdapter();
        setSubCategoryAdapter(R.array.car_sub_category_american);
        rootView.findViewById(R.id.imageview_photo1).setOnClickListener(this);
        rootView.findViewById(R.id.imageview_photo2).setOnClickListener(this);
        rootView.findViewById(R.id.imageview_photo3).setOnClickListener(this);
        return rootView;
    }

    private void setCategoryAdapter() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.car_category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerCategory.setAdapter(adapter);
        mSpinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        setSubCategoryAdapter(R.array.car_sub_category_american);
                        break;
                    case 1:
                        setSubCategoryAdapter(R.array.car_sub_category_european);
                        break;
                    case 2:
                        setSubCategoryAdapter(R.array.car_sub_category_asian);
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

    private void setSubCategoryAdapter(int arrayId) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), arrayId, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerSubCategory.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        openPhotoOptions();
        switch (v.getId()) {
            case R.id.imageview_photo1:
                mCurrentImageSelection = 1;
                break;
            case R.id.imageview_photo2:
                mCurrentImageSelection = 2;
                break;
            case R.id.imageview_photo3:
                mCurrentImageSelection = 3;
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
                    if (mProfileImagePath != null) {
                        Bitmap bitmap = Utils.getBitmapFromPath(mProfileImagePath);
                        switch (mCurrentImageSelection){
                            case 1:
                                ((ImageView)getView().findViewById(R.id.imageview_photo1)).setImageBitmap(bitmap);
                                break;
                            case 2:
                                ((ImageView)getView().findViewById(R.id.imageview_photo2)).setImageBitmap(bitmap);
                                break;
                            case 3:
                                ((ImageView)getView().findViewById(R.id.imageview_photo3)).setImageBitmap(bitmap);
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

}
