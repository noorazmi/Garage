package com.arsalan.garage.fragments;

import android.util.Log;

import com.arsalan.garage.R;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.Utils;
import com.arsalan.garage.vo.CarWashDetailsVO;

import networking.HttpConstants;
import networking.listeners.OnLoadCompleteListener;
import networking.loader.LoaderHandler;
import networking.models.HTTPModel;
import networking.models.HTTPRequest;
import networking.models.HTTPResponse;

/**
 * <p/>
 * Created by: Noor  Alam on 07/08/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class CarWashGalleryFragment extends BaseGalleryFragment {

    private static final String TAG = "CarWashGalleryFragment";
    private CarWashDetailsVO mCarWashDetailsVO;

    @Override
    void performGET() {
        HTTPRequest httpRequest = new HTTPRequest();
        httpRequest.setShowProgressDialog(true);
        String url = getArguments().getString(AppConstants.EXTRA_IMAGE_URL);
        ;
        Log.e(TAG, " ******^^^^^^^^^imageUrl URL:" + url);
        httpRequest.setUrl(url);
        httpRequest.setRequestType(HttpConstants.HTTP_REQUEST_TYPE_GET);
        LoaderHandler loaderHandler = LoaderHandler.newInstance(this, httpRequest);
        loaderHandler.setOnLoadCompleteListener(new OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(HTTPModel httpModel) {
                HTTPResponse httpResponse = (HTTPResponse) httpModel;
                String responseString = httpResponse.getResponseJSONString();
                if(responseString == null || responseString.contains("fail")){
                    Utils.showToastMessage(getActivity() ,getString(R.string.error_something_went_wrong));
                    return;
                }
                mCarWashDetailsVO =  CarWashDetailsFragment.getCarWashDetailsVo(responseString);
                setPagerAdapter(mCarWashDetailsVO.getResults().getImages());
            }
        });
        loaderHandler.loadData();
    }

}