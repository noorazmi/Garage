package com.arsalan.garage.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;

import com.arsalan.garage.R;
import com.arsalan.garage.fragments.ProductGalleryFragment;
import com.arsalan.garage.utils.AppConstants;

public class FullImageActivity extends BaseActivity implements  ProductGalleryFragment.TopBottomViewHideShowListener{

    private ProductGalleryFragment mProductGalleryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar(toolbar, getIntent().getStringExtra(AppConstants.EXTRA_TITLE), true, Gravity.CENTER);
        setFullScreenFragment();
    }

    private void setFullScreenFragment(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.EXTRA_IMAGE_URL, getIntent().getStringExtra(AppConstants.EXTRA_IMAGE_URL));
        bundle.putInt(AppConstants.EXTRA_INDEX, getIntent().getIntExtra(AppConstants.EXTRA_INDEX, 0));
        //FullImageFragment fragment = new FullImageFragment();
        mProductGalleryFragment = new ProductGalleryFragment();
        mProductGalleryFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.framelayout_container, mProductGalleryFragment).commit();
    }

    @Override
    public void setTopBottomViewVisibility() {
        if(mProductGalleryFragment != null){
            mProductGalleryFragment.showHideViews();
        }
    }
}
