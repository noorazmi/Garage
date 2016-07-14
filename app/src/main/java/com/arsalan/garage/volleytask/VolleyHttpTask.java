package com.arsalan.garage.volleytask;

import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.arsalan.garage.utils.Logger;

import org.json.JSONObject;

import java.util.Map;

/**
 * <p/>
 * Created by: Noor  Alam on 14/12/15.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class VolleyHttpTask {

    private VolleyHttpListener mVolleyHttpListener;
    private int mRequestMethod = Request.Method.GET;
    private String mJsonPayload;
    private String mUrl;
    private String mResponseModelClassFullyQualifiedName;
    private DefaultRetryPolicy mDefaultRetryPolicy;
    private String mTag;

    /**
     * By default {@link Request.Method#GET} method type will be used.
     *
     * @param url                 resource url
     * @param mVolleyHttpListener {@link VolleyHttpListener}
     */
    public VolleyHttpTask(String url, VolleyHttpListener mVolleyHttpListener) {
        this.mVolleyHttpListener = mVolleyHttpListener;
        this.mUrl = url;
    }

    /**
     * @param url                 resource url
     * @param requestMethod       on of the constants from {@link Request.Method}
     * @param mVolleyHttpListener {@link VolleyHttpListener}
     */
    public VolleyHttpTask(String url, int requestMethod, VolleyHttpListener mVolleyHttpListener) {
        this.mVolleyHttpListener = mVolleyHttpListener;
        this.mUrl = url;
        this.mRequestMethod = requestMethod;
    }

    public void setJsonPayload(JSONObject jsonObjectPayload) {
        setJsonPayload(jsonObjectPayload.toString());
    }

    private void setJsonPayload(String jsonStringPayload) {
        this.mJsonPayload = jsonStringPayload;
    }

    /**
     * Fully qualified name of the model class. The fully qualified name of a class includes the package name.
     *
     * @return
     */
    public void setResponseModelClassFullyQualifiedName(String responseModelClassFullyQualifiedName) {
        this.mResponseModelClassFullyQualifiedName = responseModelClassFullyQualifiedName;
    }

    public void setDefaultRetryPolicy(DefaultRetryPolicy defaultRetryPolicy) {
        this.mDefaultRetryPolicy = defaultRetryPolicy;
    }

    public void setTag(String tag) {
        this.mTag = tag;
    }

    public void start() {
        if (TextUtils.isEmpty(mUrl)) {
            VLogger.printLogE(VConstants.URL_EMPTY);
            return;
        }

        VLogger.printLogD("HttpRequestUrl:" + mUrl);
        VLogger.printLogD("HttpRequestJsonPayload:" + mJsonPayload);

        VolleyStringRequest req = new VolleyStringRequest(mRequestMethod, mUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                VLogger.printLogD("HttpResponseJson:" + response);
                VolleyHttpResponse volleyHttpResponse = new VolleyHttpResponse();
                if(mResponseModelClassFullyQualifiedName == null){
                    VLogger.printLogE(VConstants.RESPONSE_MODEL_CLASS_FULLY_QUALIFIED_NAME_NULL);
                    volleyHttpResponse.setJsonResponseString(response);
                    mVolleyHttpListener.onResult(volleyHttpResponse);
                    return;
                }
                volleyHttpResponse.setResponseModel(HttpUtils.getResponseModelObject(response, mResponseModelClassFullyQualifiedName));
                mVolleyHttpListener.onResult(volleyHttpResponse);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Logger.printLog("HttpResponseJson" + error);
                mVolleyHttpListener.onError(error);
            }
        }) {
            @Override
            public byte[] getBody() {
                return mJsonPayload.toString().getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return super.getHeaders();
            }
        };
        if(mDefaultRetryPolicy == null){
            req.setRetryPolicy(new DefaultRetryPolicy(VConstants.INITIAL_TIME_OUT, VConstants.NUM_OF_RETRY, VConstants.BACKOFF_MULTIPLIER));
        }else {
            req.setRetryPolicy(mDefaultRetryPolicy);
        }
        if(mTag != null){
            req.setTag(mTag);
        }

        VolleyController.getRequestQueue().add(req);
    }
}
