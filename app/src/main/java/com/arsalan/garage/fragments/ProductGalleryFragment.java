package com.arsalan.garage.fragments;


import android.util.Log;

import com.arsalan.garage.GarageApp;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.vo.ShowroomCarVo;

import networking.HttpConstants;
import networking.listeners.OnLoadCompleteListener;
import networking.loader.LoaderHandler;
import networking.models.HTTPModel;
import networking.models.HTTPRequest;
import networking.models.HTTPResponse;

public class ProductGalleryFragment extends BaseGalleryFragment{

    private ShowroomCarVo mShowroomCarVo;
    private static final String TAG = "ProductGalleryFragment";

    @Override
    void performGET() {
        HTTPRequest httpRequest = new HTTPRequest();
        httpRequest.setShowProgressDialog(true);
        //Log.e(TAG, " ******^^^^^^^^^bundle URL:" + (Urls.SHOWROOM_CAR + getArguments().getString(AppConstants.EXTRA_CAR_ID)));
        String url = getArguments().getString(AppConstants.EXTRA_IMAGE_URL) + GarageApp.DEVICE_UUID_WITH_SLASH;
        Log.e(TAG, " ******^^^^^^^^^bundle URL:" + url);
        httpRequest.setUrl(url);
        httpRequest.setRequestType(HttpConstants.HTTP_REQUEST_TYPE_GET);
        httpRequest.setValueObjectFullyQualifiedName(ShowroomCarVo.class.getName());
        LoaderHandler loaderHandler = LoaderHandler.newInstance(this, httpRequest);
        loaderHandler.setOnLoadCompleteListener(new OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(HTTPModel httpModel) {
                HTTPResponse httpResponse = (HTTPResponse) httpModel;
                mShowroomCarVo = (ShowroomCarVo) httpResponse.getValueObject();
                setPagerAdapter(mShowroomCarVo.getResults().getImages());
            }
        });
        loaderHandler.loadData();
    }
}