package com.arsalan.garage.volleytask;

/**
 * <p/>
 * Created by: Noor  Alam on 14/12/15.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
class VConstants {

    /**
     * Request Retry Policy Constant
     */
    public static final int INITIAL_TIME_OUT = 30*1000;
    public static final int NUM_OF_RETRY = 2;
    public static final float BACKOFF_MULTIPLIER = 2;
    public static final String POST_TOKEN_TASK = "postTokenTask";

    public static final String URL_EMPTY = "Url can not be null or empty";
    public static final String VOLLEY_CONTROLLER_NOT_INITIALIZED = "VolleyController is not initialized yet. Please initialize it by calling method VolleyConroller.initVolleyRequestQueueAndImageLoader(Context)";
    public static final String RESPONSE_MODEL_CLASS_FULLY_QUALIFIED_NAME_NULL = "The Response Model fully qualified name is null. Please set it by calling VolleyHttpTask.setResponseModelClassFullyQualifiedName(String) before calling VolleyHttpTask.start() to populate the data in the model";
}
