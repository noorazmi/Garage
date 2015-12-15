package networking.models;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import networking.dialogs.DialogParams;

/**
 * Created by rajendra on 17/9/14.
 */
public class HTTPRequest implements Parcelable {

    private String url;
    private Bundle paramBundle;
    private String requestType;
    private String jsonPayload;
    private String valueObjectFullyQualifiedName;
    private boolean showProgressDialog = false;
    private DialogParams dialogParams;

    public HTTPRequest() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Bundle getParamBundle() {
        return paramBundle;
    }

    public void setParamBundle(Bundle paramBundle) {
        this.paramBundle = paramBundle;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getJSONPayload() {
        return jsonPayload;
    }

    public void setJSONPayload(String jsonPayload) {
        this.jsonPayload = jsonPayload;
    }

    public String getValueObjectFullyQualifiedName() {
        return valueObjectFullyQualifiedName;
    }

    public void setValueObjectFullyQualifiedName(String valueObjectFullyQualifiedName) {
        this.valueObjectFullyQualifiedName = valueObjectFullyQualifiedName;
    }

    public boolean isShowProgressDialog() {
        return showProgressDialog;
    }

    public void setShowProgressDialog(boolean showProgressDialog) {
        this.showProgressDialog = showProgressDialog;
    }

    public void setShowProgressDialog(boolean showProgressDialog, DialogParams dialogParams) {
        this.showProgressDialog = showProgressDialog;
        this.dialogParams = dialogParams;
    }

    public DialogParams getDialogParams() {
        return dialogParams;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeBundle(paramBundle);
        dest.writeString(requestType);
        dest.writeString(jsonPayload);
        dest.writeByte((byte) (showProgressDialog ? 1 : 0));
    }


    public static final Creator<HTTPRequest> CREATOR = new Creator<HTTPRequest>() {
        @Override
        public HTTPRequest createFromParcel(Parcel parcel) {

            return new HTTPRequest(parcel);
        }

        @Override
        public HTTPRequest[] newArray(int size) {
            return new HTTPRequest[size];
        }
    };

    public HTTPRequest(Parcel parcel) {
        url = parcel.readString();
        paramBundle = parcel.readBundle();
        requestType = parcel.readString();
        jsonPayload = parcel.readString();
        showProgressDialog = parcel.readByte() == 1;
    }
}

