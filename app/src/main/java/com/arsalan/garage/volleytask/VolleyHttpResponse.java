package com.arsalan.garage.volleytask;

/**
 * <p/>
 * Created by: Noor  Alam on 14/12/15.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class VolleyHttpResponse{

    private ResponseModel mResponseModel;
    private String mJsonResponseString;



    private ResponseModel getResult(){
        return mResponseModel;
    }

    void setResponseModel(ResponseModel mResponseModel) {
        this.mResponseModel = mResponseModel;
    }

    public ResponseModel getResponseModel() {
        return mResponseModel;
    }

    public String getJsonResponseString() {
        return mJsonResponseString;
    }

    void setJsonResponseString(String mJsonResponseString) {
        this.mJsonResponseString = mJsonResponseString;
    }
}
