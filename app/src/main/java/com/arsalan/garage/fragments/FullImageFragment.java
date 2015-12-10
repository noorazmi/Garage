package com.arsalan.garage.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arsalan.garage.R;
import com.arsalan.garage.adapters.ZoomImageViewPagerAdapter;
import com.arsalan.garage.models.ImageInfo;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.Utils;
import com.arsalan.garage.vo.ShowroomCarVo;

import java.util.ArrayList;

import networking.HttpConstants;
import networking.listeners.OnLoadCompleteListener;
import networking.loader.LoaderHandler;
import networking.models.HTTPModel;
import networking.models.HTTPRequest;
import networking.models.HTTPResponse;

public class FullImageFragment extends Fragment {

    private static final String TAG = "FullImageFragment";
    private ViewPager mViewPagerCarImages;
    private ShowroomCarVo mShowroomCarVo;

    public FullImageFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_full_image, container, false);
        mViewPagerCarImages = (ViewPager) rootView.findViewById(R.id.viewpager_car_images);
        if (Utils.isNetworkAvailable(getActivity())) {
            performGET();
        } else {
            Utils.showSnackBar(getActivity(), getString(R.string.no_network_connection));
        }
        return rootView;
    }

    private void setPagerAdapter() {
        ArrayList<ImageInfo> carImageArrayList = mShowroomCarVo.getResults().getImages();
        ZoomImageViewPagerAdapter adapter = new ZoomImageViewPagerAdapter(getFragmentManager(), carImageArrayList);
        mViewPagerCarImages.setAdapter(adapter);
        mViewPagerCarImages.setCurrentItem(getArguments().getInt(AppConstants.EXTRA_INDEX));
    }

    private void performGET() {
        HTTPRequest httpRequest = new HTTPRequest();
        httpRequest.setShowProgressDialog(true);
        //Log.e(TAG, " ******^^^^^^^^^bundle URL:" + (Urls.SHOWROOM_CAR + getArguments().getString(AppConstants.EXTRA_CAR_ID)));
        Log.e(TAG, " ******^^^^^^^^^bundle URL:" + getArguments().getString(AppConstants.EXTRA_IMAGE_URL));
        httpRequest.setUrl(getArguments().getString(AppConstants.EXTRA_IMAGE_URL));
        httpRequest.setRequestType(HttpConstants.HTTP_REQUEST_TYPE_GET);
        httpRequest.setValueObjectFullyQualifiedName(ShowroomCarVo.class.getName());
        LoaderHandler loaderHandler = LoaderHandler.newInstance(this, httpRequest);
        loaderHandler.setOnLoadCompleteListener(new OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(HTTPModel httpModel) {
                HTTPResponse httpResponse = (HTTPResponse) httpModel;
                mShowroomCarVo = (ShowroomCarVo) httpResponse.getValueObject();
                setPagerAdapter();
            }
        });
        loaderHandler.loadData();
    }

//    @Override
//    public void onResume() {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                getActivity().getActionBar().hide();
//            }
//        }, 2000);
//        super.onResume();
//    }
}
