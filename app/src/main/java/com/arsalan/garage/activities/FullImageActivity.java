package com.arsalan.garage.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;

import com.arsalan.garage.R;
import com.arsalan.garage.fragments.BaseGalleryFragment;
import com.arsalan.garage.fragments.CarWashGalleryFragment;
import com.arsalan.garage.fragments.MarineBeatFishingGalleryFragment;
import com.arsalan.garage.fragments.ProductGalleryFragment;
import com.arsalan.garage.fragments.UserGalleryFragment;
import com.arsalan.garage.utils.AppConstants;

public class FullImageActivity extends BaseActivity implements ProductGalleryFragment.TopBottomViewHideShowListener {

    private BaseGalleryFragment mBaseGalleryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar(toolbar, getIntent().getStringExtra(AppConstants.EXTRA_TITLE), true, Gravity.CENTER);
        setFullScreenFragment();
    }

    private void setFullScreenFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.EXTRA_IMAGE_URL, getIntent().getStringExtra(AppConstants.EXTRA_IMAGE_URL));
        bundle.putInt(AppConstants.EXTRA_INDEX, getIntent().getIntExtra(AppConstants.EXTRA_INDEX, 0));
        String galleryType = getIntent().getStringExtra(AppConstants.EXTRA_GALLERY_FOR);
        if (galleryType == null || galleryType.equals(AppConstants.EXTRA_GALLERY_FOR_MARINE_SHOWROOM)) {
            mBaseGalleryFragment = new ProductGalleryFragment();
        }else if (galleryType == null || galleryType.equals(AppConstants.EXTRA_GALLERY_FOR_CAR_WASH) ) {
            mBaseGalleryFragment = new CarWashGalleryFragment();
        } else if (galleryType.equals(AppConstants.EXTRA_GALLERY_USER_GALLERY)) {
            mBaseGalleryFragment = new UserGalleryFragment();
        }else if (galleryType.equals(AppConstants.EXTRA_GALLERY_FOR_MARINE_BOAT_FISHING)) {
            mBaseGalleryFragment = new MarineBeatFishingGalleryFragment();
        }
        mBaseGalleryFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.framelayout_container, mBaseGalleryFragment).commit();
    }

    @Override
    public void setTopBottomViewVisibility() {
        if (mBaseGalleryFragment != null) {
            mBaseGalleryFragment.showHideViews();
        }
    }
}
