package com.arsalan.garage.fragments;

import android.util.Log;

import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.vo.ScrapUserDetailsData;

import networking.HttpConstants;
import networking.listeners.OnLoadCompleteListener;
import networking.loader.LoaderHandler;
import networking.models.HTTPModel;
import networking.models.HTTPRequest;
import networking.models.HTTPResponse;

/**
 * <p/>
 * Created by: Noor  Alam on 14/05/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class ScrapUserGalleryFragment extends BaseGalleryFragment {

    private static final String TAG = "ScrapUserGalleryFrag";
    private ScrapUserDetailsData mScrapUserDetailsData;
    @Override
    void performGET() {
        HTTPRequest httpRequest = new HTTPRequest();
        httpRequest.setShowProgressDialog(true);
        //Log.e(TAG, " ******^^^^^^^^^bundle URL:" + (Urls.SHOWROOM_CAR + getArguments().getString(AppConstants.EXTRA_CAR_ID)));
        Log.e(TAG, " ******^^^^^^^^^bundle URL:" + getArguments().getString(AppConstants.EXTRA_IMAGE_URL));
        httpRequest.setUrl(getArguments().getString(AppConstants.EXTRA_IMAGE_URL));
        httpRequest.setRequestType(HttpConstants.HTTP_REQUEST_TYPE_GET);
        httpRequest.setValueObjectFullyQualifiedName(ScrapUserDetailsData.class.getName());
        LoaderHandler loaderHandler = LoaderHandler.newInstance(this, httpRequest);
        loaderHandler.setOnLoadCompleteListener(new OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(HTTPModel httpModel) {
                HTTPResponse httpResponse = (HTTPResponse) httpModel;
                mScrapUserDetailsData = (ScrapUserDetailsData) httpResponse.getValueObject();
                setPagerAdapter(mScrapUserDetailsData.getResults().getImages());
            }
        });
        loaderHandler.loadData();
    }
}