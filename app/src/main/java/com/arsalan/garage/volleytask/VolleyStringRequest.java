package com.arsalan.garage.volleytask;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.arsalan.garage.GarageApp;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Noor Alam on 15/12/15.
 */
class VolleyStringRequest extends com.android.volley.toolbox.StringRequest {

    /**
     * @param method
     * @param url
     * @param listener
     * @param errorListener
     */
    public VolleyStringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    /* (non-Javadoc)
     * @see com.android.volley.toolbox.StringRequest#parseNetworkResponse(com.android.volley.NetworkResponse)
     */
    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        // since we don't know which of the two underlying network vehicles
        // will Volley use, we have to handle and store session cookies manually
        GarageApp.getInstance().checkSessionCookie(response.headers);
        return super.parseNetworkResponse(response);
    }

    /* (non-Javadoc)
     * @see com.android.volley.Request#getHeaders()
     */
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = super.getHeaders();
        if (headers == null || headers.equals(Collections.emptyMap())) {
            headers = new HashMap<String, String>();
            headers.put("Content-Type", "application/json");
        }
        GarageApp.getInstance().addSessionCookie(headers);
        return headers;
    }
}