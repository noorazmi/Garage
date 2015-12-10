package networking.loader;


import android.content.AsyncTaskLoader;

import networking.HttpConstants;
import networking.HttpUtils;
import networking.Logger;
import networking.models.HTTPModel;
import networking.models.HTTPRequest;


/**
 * Created by noor on 28/04/15.
 */
public class HttpAsyncTaskLoader extends AsyncTaskLoader<HTTPModel> {
    private static final String TAG = "HttpAsyncTaskLoader";
    private HTTPRequest mHTTRequest;
    public HttpAsyncTaskLoader(HTTPRequest httpRequest) {
        //Pass the reference of Context object in super constructor here.
        super(LoaderHandler.getContext());
        mHTTRequest = httpRequest;
    }


    /**
     * Subclasses must implement this to take care of loading their data,
     * as per {@link #startLoading()}.  This is not called by clients directly,
     * but as a result of a call to {@link #startLoading()}.
     */
    @Override
    protected void onStartLoading() {
        Logger.i(TAG, "*****onStartLoading() LoaderId:" + getId());
        super.onStartLoading();

    }

    @Override
    public HTTPModel loadInBackground() {
        Logger.i(TAG, "*****loadInBackground() LoaderId:" + getId());
            HTTPModel httpModel = null;
        if(mHTTRequest.getRequestType().equals(HttpConstants.HTTP_REQUEST_TYPE_POST)){
            httpModel = HttpUtils.doPost(mHTTRequest);
        }else if(mHTTRequest.getRequestType().equals(HttpConstants.HTTP_REQUEST_TYPE_GET)){
            httpModel = HttpUtils.doGet(mHTTRequest);
        }

        return httpModel;
    }

    /**
     * Subclasses must implement this to take care of stopping their loader,
     * as per {@link #stopLoading()}.  This is not called by clients directly,
     * but as a result of a call to {@link #stopLoading()}.
     * This will always be called from the process's main thread.
     */
    @Override
    protected void onStopLoading() {
        Logger.i(TAG, "*****onStopLoading() LoaderId:" + getId());
        super.onStopLoading();

    }

    /**
     * Subclasses must implement this to take care of resetting their loader,
     * as per {@link #reset()}.  This is not called by clients directly,
     * but as a result of a call to {@link #reset()}.
     * This will always be called from the process's main thread.
     */
    @Override
    protected void onReset() {
        Logger.i(TAG, "*****onReset() LoaderId:" + getId());
        super.onReset();

    }

    /**
     * Called if the task was canceled before it was completed.  Gives the class a chance
     * to properly dispose of the result.
     *
     * @param data
     */
    @Override
    public void onCanceled(HTTPModel data) {
        Logger.i(TAG, "*****onCanceled() LoaderId:" + getId());
        super.onCanceled(data);
    }

    /**
     * Sends the result of the load to the registered listener. Should only be called by subclasses.
     * <p/>
     * Must be called from the process's main thread.
     *
     * @param data the result of the load
     */
    @Override
    public void deliverResult(HTTPModel data) {
        Logger.i(TAG, "*****deliverResult() LoaderId:" + getId());
        super.deliverResult(data);

    }


}
