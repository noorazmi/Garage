package com.arsalan.garage.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.arsalan.garage.R;
import com.arsalan.garage.activities.FullImageActivity;
import com.arsalan.garage.adapters.AlwakalatAgencyDescriptionCarsViewPagerAdapter;
import com.arsalan.garage.adapters.AlwakalatAgencyDescriptionTabViewPagerAdapter;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.Urls;
import com.arsalan.garage.utils.Utils;
import com.arsalan.garage.vo.ShowroomCarVo;

import java.util.ArrayList;

import networking.HttpConstants;
import networking.listeners.OnLoadCompleteListener;
import networking.loader.LoaderHandler;
import networking.models.HTTPModel;
import networking.models.HTTPRequest;
import networking.models.HTTPResponse;

/**
 * <p/>
 * Created by: Noor  Alam on 18/10/15.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class AlwakalatAgencyDescriptionFragment extends Fragment {

    private ViewPager mViewpagerDescription;
    private TabLayout mTablayoutDescription;
    private ViewPager mViewPagerCarImages;
    private String TAG = "AlwakalatAgencyDescriptionFragment";
    private ShowroomCarVo mShowroomCarVo;
    private GestureDetector mGestureDetector;
    private int mCurrentCarIndex = 0;

    public AlwakalatAgencyDescriptionFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_alwakalat_agency_description, container, false);
        mViewPagerCarImages = (ViewPager) rootView.findViewById(R.id.viewpager_car_images);
        mGestureDetector = new GestureDetector(getActivity(), mSimpleOnGestureListener);
        mViewPagerCarImages.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                mGestureDetector.onTouchEvent(event);
                return false;
            }
        });
        mViewpagerDescription = (ViewPager) rootView.findViewById(R.id.viewpager_description);
        mTablayoutDescription = (TabLayout) rootView.findViewById(R.id.tablayout_description);
        if(Utils.isNetworkAvailable(getActivity())){
            performGET();
        }else {
            Utils.showSnackBar(getActivity(), getString(R.string.no_network_connection));
        }
        return rootView;
    }

    private void setPagerAdapter() {
        ArrayList<ShowroomCarVo.CarImage> carImageArrayList = mShowroomCarVo.getResults().getImages();
        AlwakalatAgencyDescriptionCarsViewPagerAdapter adapter = new AlwakalatAgencyDescriptionCarsViewPagerAdapter(getFragmentManager(), carImageArrayList);
        mViewPagerCarImages.setAdapter(adapter);
    }
    private void setDecriptionPagerAdapter(){
        AlwakalatAgencyDescriptionTabViewPagerAdapter seasonsFragmentStatePagerAdapter = new AlwakalatAgencyDescriptionTabViewPagerAdapter(getActivity(), getChildFragmentManager(), mShowroomCarVo);
        mViewpagerDescription.setAdapter(seasonsFragmentStatePagerAdapter);
        mTablayoutDescription.setupWithViewPager(mViewpagerDescription);
        new Handler().postAtTime(new Runnable() {
            @Override
            public void run() {
                mViewpagerDescription.setCurrentItem(3, false);
                //mTablayoutDescription.getTabAt(3).select();
            }
        }, 500);
    }


    private void performGET(){
        HTTPRequest httpRequest = new HTTPRequest();
        httpRequest.setShowProgressDialog(true);
        //Bundle bundle = getArguments();
        Log.e(TAG, " ******^^^^^^^^^bundle URL:" + (Urls.SHOWROOM_CAR + getArguments().getString(AppConstants.EXTRA_CAR_ID)));
        httpRequest.setUrl(Urls.SHOWROOM_CAR + getArguments().getString(AppConstants.EXTRA_CAR_ID));
        httpRequest.setRequestType(HttpConstants.HTTP_REQUEST_TYPE_GET);
        httpRequest.setValueObjectFullyQualifiedName(ShowroomCarVo.class.getName());
        LoaderHandler loaderHandler = LoaderHandler.newInstance(this, httpRequest);
        loaderHandler.setOnLoadCompleteListener(new OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(HTTPModel httpModel) {
                HTTPResponse httpResponse = (HTTPResponse) httpModel;
                mShowroomCarVo = (ShowroomCarVo) httpResponse.getValueObject();
                setPagerAdapter();
                setDecriptionPagerAdapter();
            }
        });
        loaderHandler.loadData();
    }


    private GestureDetector.SimpleOnGestureListener mSimpleOnGestureListener =  new  GestureDetector.SimpleOnGestureListener(){

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            openFullImageActivity();
            return super.onSingleTapConfirmed(e);
        }
    };


    private void openFullImageActivity(){
        Intent intent = new Intent(getActivity(), FullImageActivity.class);
        intent.putExtra(AppConstants.EXTRA_CAR_ID, getArguments().getString(AppConstants.EXTRA_CAR_ID));
        intent.putExtra(AppConstants.EXTRA_INDEX, mViewPagerCarImages.getCurrentItem());
        startActivity(intent);
    }
}
