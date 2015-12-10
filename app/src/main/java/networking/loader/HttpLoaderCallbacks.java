package networking.loader;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;

import networking.HttpConstants;
import networking.Logger;
import networking.listeners.OnLoaderFinishedListener;
import networking.models.HTTPModel;
import networking.models.HTTPRequest;


/**
 * Created by noor on 28/04/15.
 */
public class HttpLoaderCallbacks<M> implements LoaderManager.LoaderCallbacks<HTTPModel> {

    private static final String TAG = "HttpLoaderCallbacks";
    //private OnLoadCompleteListener mOnLoadCompleteListener;
    private OnLoaderFinishedListener mOnLoaderFinishedListener;


    public HttpLoaderCallbacks(OnLoaderFinishedListener mOnLoaderFinishedListener) {
        //mOnLoadCompleteListener = onLoadCompleteListener;
        this.mOnLoaderFinishedListener = mOnLoaderFinishedListener;
    }

    @Override
    public Loader<HTTPModel> onCreateLoader(int id, Bundle bundle) {
        HTTPRequest httpRequest = bundle.getParcelable(HttpConstants.HTTP_REQUEST);
        Logger.i(TAG, "*****onCreateLoader() LoaderId:" + id + " Bundle:" + bundle.toString() + " requestParamBundle:" + httpRequest.getParamBundle());
        HttpAsyncTaskLoader httpAsyncTaskLoader = new HttpAsyncTaskLoader(httpRequest);
        return httpAsyncTaskLoader;
    }



    @Override
    public void onLoadFinished(Loader<HTTPModel> loader, HTTPModel data) {
        Logger.i(TAG, "*****onLoadFinished() LoaderId:" + loader.getId());
        mOnLoaderFinishedListener.onLoaderFinished(loader, data);

    }

    @Override
    public void onLoaderReset(Loader<HTTPModel> loader) {
        Logger.i(TAG, "*****onLoaderReset() LoaderId:" + loader.getId());

    }
}
