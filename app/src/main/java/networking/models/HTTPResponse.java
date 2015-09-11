package networking.models;

/**
 * Created by rajendra on 17/9/14.
 */
public class HTTPResponse implements HTTPModel {
    private boolean isError;
    private String responseJSONString = "";
    private String errorMsg = "";
    private int httpStatusCode;
    private ValueObject valueObject;
    //The loader id of loader which downloaded this data.
    private int loaderId;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getResponseJSONString() {
        return responseJSONString;
    }

    public void setResponseJSONString(String responseJSONString) {
        this.responseJSONString = responseJSONString;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean isError) {
        this.isError = isError;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public ValueObject getValueObject() {
        return valueObject;
    }

    public void setValueObject(ValueObject valueObject) {
        this.valueObject = valueObject;
    }

    /**
     * Returns the id of the loader which downloaded this data.
     * @return
     */
    public int getLoaderId() {
        return loaderId;
    }

    /**
     * Sets the Id of the loader which downloaded this data. You don't have to set it yourself, the loader which downloaded this data will set it internally
      * @param loaderId
     */

    public void setLoaderId(int loaderId) {
        this.loaderId = loaderId;
    }
}
