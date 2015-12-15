package networking.loader;


import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Loader;
import android.os.Bundle;

import networking.HttpConstants;
import networking.Logger;
import networking.dialogs.DialogHandler;
import networking.dialogs.DialogParams;
import networking.listeners.OnLoadCompleteListener;
import networking.listeners.OnLoaderFinishedListener;
import networking.models.HTTPModel;
import networking.models.HTTPRequest;
import networking.models.HTTPResponse;


/**
 * Created by noor on 29/04/15.
 */
public class LoaderHandler {

    // We will use this as loader id. Increase it by 1 every time you are going to create a new loader.
    private static int loaderId = -1;
    private static final String TAG = "LoaderHandler";
    private Activity mActivity;
    private HTTPRequest mHttpRequest;
    private OnLoadCompleteListener mOnLoadCompleteListener = null;
    private LoaderManager mLoaderManager;
    private DialogHandler mDialogHandler;
    private static Context sContext;
    private static boolean loggerEnabled = false;
    /** Loader id associated created by this LoaderHandler instance */
    private int instanceLoaderId;

    public LoaderHandler(Activity activity, HTTPRequest httpRequest, LoaderManager loaderManager) {
        this.mActivity = activity;
        this.mHttpRequest = httpRequest;
        this.mLoaderManager = loaderManager;
        mLoaderManager.enableDebugLogging(loggerEnabled);
    }

    public static Context getContext() {
        if (sContext == null) {
            throw new NullPointerException(HttpConstants.APPLICATION_CONTEXT_NOT_SET_MESSAGE);
        }
        return sContext;
    }

    public static void setContext(Context context) {
        if (context == null) {
            throw new IllegalArgumentException(HttpConstants.ILLEGAL_ARGUMENT_EXCEPTION_CONTEXT_NULL);
        }
        LoaderHandler.sContext = context.getApplicationContext();
    }

    public static boolean isLoggerEnabled() {
        return loggerEnabled;
    }

    /**
     * Enable and disable the log information of the LoaderHandler.
     *
     * @param loggerEnabled If true, the logger will print the log info in logcat.
     */
    public static void setLoggerEnabled(boolean loggerEnabled) {
        LoaderHandler.loggerEnabled = loggerEnabled;
    }

    /**
     * Will be called using activity reference
     *
     * @param fragmentActivity
     * @param httpRequest
     * @return
     */
    public static LoaderHandler newInstance(Activity fragmentActivity, HTTPRequest httpRequest) {
        return new LoaderHandler(fragmentActivity, httpRequest, fragmentActivity.getLoaderManager());
    }

    /**
     * Will be called using Fragment reference
     *
     * @param fragment
     * @param httpRequest
     * @return
     */
    public static LoaderHandler newInstance(Fragment fragment, HTTPRequest httpRequest) {
        return new LoaderHandler(fragment.getActivity(), httpRequest, fragment.getLoaderManager());
    }

    public void setOnLoadCompleteListener(OnLoadCompleteListener onLoadCompleteListener) {
        this.mOnLoadCompleteListener = onLoadCompleteListener;
    }

    /**
     * Start loading data from server. Data will be received in registered {@link OnLoadCompleteListener} listener.
     */
    public void loadData() {
        if (mHttpRequest.isShowProgressDialog()) {
            if (mDialogHandler == null) {
                mDialogHandler = new DialogHandler(mActivity);
            }

            DialogParams dialogParams = mHttpRequest.getDialogParams();
            if ( dialogParams != null) {
                if(dialogParams.isCancellable() && dialogParams.getOnCancelListener() == null){
                    dialogParams.setOnCancelListener(mOnProgressDialogCancelListener);
                }
                mDialogHandler.showProgressDialog(mHttpRequest.getDialogParams());
            } else {
                mDialogHandler.showDefaultProgressDialog(mOnProgressDialogCancelListener);
            }

        }
        Bundle bundle = new Bundle();
        bundle.putParcelable(HttpConstants.HTTP_REQUEST, mHttpRequest);
        LoaderManager.LoaderCallbacks<HTTPModel> myLoaderCallbacks = new HttpLoaderCallbacks<HTTPModel>(new OnLoaderFinishedListener() {
            @Override
            public void onLoaderFinished(Loader loader, HTTPModel data) {
                if (mOnLoadCompleteListener != null) {
                    ((HTTPResponse) data).setLoaderId(loader.getId());
                    mOnLoadCompleteListener.onLoadComplete(data);
                }
                //loader.reset();
                mLoaderManager.destroyLoader(loader.getId());
                if (mDialogHandler != null) {
                    mDialogHandler.dismissProgressDialog();
                }

            }
        });

        instanceLoaderId = ++loaderId;
        Logger.i(TAG, "*****Creating Loader with Id:"+instanceLoaderId);
        mLoaderManager.initLoader(instanceLoaderId, bundle, myLoaderCallbacks).forceLoad();
    }

    private DialogInterface.OnCancelListener mOnProgressDialogCancelListener = new DialogInterface.OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialog) {
            mActivity.getLoaderManager().destroyLoader(instanceLoaderId+11);
            Logger.i(TAG, "*****Http request canceled:"+instanceLoaderId);
            Logger.i(TAG, "*****Destroying Loader with Id:"+instanceLoaderId);
        }
    };

}
