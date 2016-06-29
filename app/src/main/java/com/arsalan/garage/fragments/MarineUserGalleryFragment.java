package com.arsalan.garage.fragments;

import android.util.Log;

import com.arsalan.garage.GarageApp;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.vo.MarineUserDetailsData;

import networking.HttpConstants;
import networking.listeners.OnLoadCompleteListener;
import networking.loader.LoaderHandler;
import networking.models.HTTPModel;
import networking.models.HTTPRequest;
import networking.models.HTTPResponse;

/**
 * <p/>
 * Created by: Noor  Alam on 13/05/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class MarineUserGalleryFragment extends BaseGalleryFragment {

    private static final String TAG = "MarineUserGalleryFrag";
    private MarineUserDetailsData mMarineUserDetailsData;
    @Override
    void performGET() {
        HTTPRequest httpRequest = new HTTPRequest();
        httpRequest.setShowProgressDialog(true);
        String url = getArguments().getString(AppConstants.EXTRA_IMAGE_URL) + GarageApp.DEVICE_UUID_WITH_SLASH;
        Log.e(TAG, " ******^^^^^^^^^bundle URL:" + url) ;
        httpRequest.setUrl(url);
        httpRequest.setRequestType(HttpConstants.HTTP_REQUEST_TYPE_GET);
        httpRequest.setValueObjectFullyQualifiedName(MarineUserDetailsData.class.getName());
        LoaderHandler loaderHandler = LoaderHandler.newInstance(this, httpRequest);
        loaderHandler.setOnLoadCompleteListener(new OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(HTTPModel httpModel) {
                HTTPResponse httpResponse = (HTTPResponse) httpModel;
                mMarineUserDetailsData = (MarineUserDetailsData) httpResponse.getValueObject();
                setPagerAdapter(mMarineUserDetailsData.getResults().getImages());
            }
        });
        loaderHandler.loadData();
    }
}