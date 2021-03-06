package com.arsalan.garage.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import com.arsalan.garage.R;
import com.arsalan.garage.adapters.GalleryImageAdapter;
import com.arsalan.garage.adapters.GalleryPagerAdapter;
import com.arsalan.garage.models.ImageInfo;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.Utils;

import java.util.ArrayList;

/**
 * <p/>
 * Created by: Noor  Alam on 13/05/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public abstract class BaseGalleryFragment extends Fragment implements AdapterView.OnItemSelectedListener, ViewSwitcher.ViewFactory {
    private Gallery mGallery;
    private com.arsalan.garage.uicomponents.ProductGalleryViewPager mViewPagerGallery;
    private String TAG = ProductGalleryFragment.class.getSimpleName();
    private boolean isFirstTime = true;
    private boolean mIsTopBottomViewHidden = false;


    public BaseGalleryFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_full_image, container, false);
        mGallery = (Gallery) rootView.findViewById(R.id.gallery);
        mViewPagerGallery = (com.arsalan.garage.uicomponents.ProductGalleryViewPager) rootView.findViewById(R.id.viewpager_car_images);
        mGallery.setOnItemSelectedListener(this);
        if (Utils.isNetworkAvailable(getActivity())) {
            performGET();
        } else {
            Utils.showSnackBar(getActivity(), getString(R.string.no_network_connection));
        }
        return rootView;
    }

    abstract void performGET();

    protected void setPagerAdapter(ArrayList<ImageInfo> carImageArrayList) {
        if (getActivity() == null) {
            return;
        }
        if (carImageArrayList.size() > 1) {
            mGallery.setAdapter(new GalleryImageAdapter(getActivity(), carImageArrayList));
            mGallery.setVisibility(View.VISIBLE);
        } else {
            mGallery.setVisibility(View.GONE);
        }
        //mGallery.setSelection(getArguments().getInt(AppConstants.EXTRA_INDEX));
        GalleryPagerAdapter galleryPagerAdapter = new GalleryPagerAdapter(carImageArrayList, getFragmentManager());
        mViewPagerGallery.setOffscreenPageLimit(10);
        mViewPagerGallery.setAdapter(galleryPagerAdapter);
        mViewPagerGallery.setOnPageChangeListener(mOnPageChangeListener);
    }


    public void showHideViews() {
        if (!mIsTopBottomViewHidden) {
            hideViews();
            mIsTopBottomViewHidden = true;
        } else {

            showViews();
            mIsTopBottomViewHidden = false;
        }
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        if (isFirstTime) {
            changeBorderForSelectedImage(getArguments().getInt(AppConstants.EXTRA_INDEX));
            setImage(getArguments().getInt(AppConstants.EXTRA_INDEX));
            isFirstTime = false;
        } else {
            changeBorderForSelectedImage(position);
            setImage(position);
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
    }


    @Override
    public View makeView() {
        ImageView i = new ImageView(getActivity());
        i.setScaleType(ImageView.ScaleType.FIT_CENTER);
        i.setLayoutParams(new ImageSwitcher.LayoutParams(Gallery.LayoutParams.MATCH_PARENT, Gallery.LayoutParams.MATCH_PARENT));
        return i;
    }

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            mGallery.setSelection(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    private void setImage(int position) {
        mViewPagerGallery.setCurrentItem(position);
    }


    private void changeBorderForSelectedImage(int selectedItemPos) {
        int count = mGallery.getChildCount();
        for (int i = 0; i < count; i++) {
            ImageView imageView = (ImageView) mGallery.getChildAt(i);
            imageView.setBackgroundResource(0);
            imageView.setPadding(3, 3, 3, 3);
        }

        ImageView imageView = (ImageView) mGallery.getSelectedView();
        imageView.setBackgroundResource(R.drawable.gallery_image_border_selector);
        imageView.setPadding(3, 3, 3, 3);
    }

    private void hideViews() {
        getActivity().findViewById(R.id.toolbar).animate().translationY(-getActivity().findViewById(R.id.toolbar).getHeight()).setInterpolator(new AccelerateInterpolator(2));

        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mGallery.getLayoutParams();
        int fabBottomMargin = lp.bottomMargin;
        mGallery.animate().translationY(mGallery.getHeight() + fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
    }

    private void showViews() {
        getActivity().findViewById(R.id.toolbar).animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
        mGallery.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
    }

    public interface TopBottomViewHideShowListener {
        void setTopBottomViewVisibility();
    }

}
