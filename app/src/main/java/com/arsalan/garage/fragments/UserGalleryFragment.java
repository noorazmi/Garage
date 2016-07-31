package com.arsalan.garage.fragments;

import android.util.Log;

import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.vo.UserDetailsData;

import networking.HttpConstants;
import networking.listeners.OnLoadCompleteListener;
import networking.loader.LoaderHandler;
import networking.models.HTTPModel;
import networking.models.HTTPRequest;
import networking.models.HTTPResponse;

/**
 * <p/>
 * Created by: Noor  Alam on 01/08/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class UserGalleryFragment extends BaseGalleryFragment {

    private static final String TAG = "UserGalleryFragment";
    private UserDetailsData mUserDetailsData;

    @Override
    void performGET() {
        HTTPRequest httpRequest = new HTTPRequest();
        httpRequest.setShowProgressDialog(true);
        String url = getArguments().getString(AppConstants.EXTRA_IMAGE_URL);
        ;
        Log.e(TAG, " ******^^^^^^^^^imageUrl URL:" + url);
        httpRequest.setUrl(url);
        httpRequest.setRequestType(HttpConstants.HTTP_REQUEST_TYPE_GET);
        httpRequest.setValueObjectFullyQualifiedName(UserDetailsData.class.getName());
        LoaderHandler loaderHandler = LoaderHandler.newInstance(this, httpRequest);
        loaderHandler.setOnLoadCompleteListener(new OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(HTTPModel httpModel) {
                HTTPResponse httpResponse = (HTTPResponse) httpModel;
                mUserDetailsData = (UserDetailsData) httpResponse.getValueObject();
                if (mUserDetailsData == null) {
                    return;
                }else if(mUserDetailsData.getResults() == null){
                    return;
                }

                setPagerAdapter(mUserDetailsData.getResults().getImages());
            }
        });
        loaderHandler.loadData();
    }
}