package com.arsalan.garage.volleytask;

import com.arsalan.garage.utils.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * <p/>
 * Created by: Noor  Alam on 14/12/15.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
class HttpUtils {

    /**
     * Returns ResponseModel after populating it using JSONString passed in this method.
     *
     * @param resultJson
     * @param responseModelClassFullyQualifiedName Fully qualified name of the class extending the ValueObject interface
     * @return
     */
    protected static ResponseModel getResponseModelObject(String resultJson, String responseModelClassFullyQualifiedName) {
        try {
            Class<? extends ResponseModel> valueObjectClass = (Class<? extends ResponseModel>) Class.forName(responseModelClassFullyQualifiedName);
            return getModelFromJsonString(resultJson, valueObjectClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //Parsing was not successful, we are returning null.
        return null;
    }

    /**
     * @param response                 json string obtained from the server
     * @param responseModelClassObject Class of {@link ResponseModel} base class in which we have to fill the data from json string
     * @return ValueObject after populating it with data obtained from json string
     */
    private static ResponseModel getModelFromJsonString(String response, Class responseModelClassObject) {
        Gson gson = new GsonBuilder().create();
        ResponseModel valueObject = null;
        try {
            valueObject = (ResponseModel) gson.fromJson(response, responseModelClassObject);
        } catch (com.google.gson.JsonSyntaxException e) {
            Logger.printError("com.google.gson.JsonSyntaxException:" + e.getMessage());
        }
        return valueObject;
    }
}
