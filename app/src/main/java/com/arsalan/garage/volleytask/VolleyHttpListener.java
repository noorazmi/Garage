package com.arsalan.garage.volleytask;

import com.android.volley.VolleyError;

/**
 * <p/>
 * Created by: Noor  Alam on 14/12/15.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public interface VolleyHttpListener {
    void onResult(VolleyHttpResponse volleyHttpResponse);
    void onError(VolleyError error);
}
