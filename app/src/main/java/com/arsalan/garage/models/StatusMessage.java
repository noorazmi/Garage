package com.arsalan.garage.models;

import com.arsalan.garage.volleytask.ResponseModel;

/**
 * <p/>
 * Created by: Noor  Alam on 02/07/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class StatusMessage implements ResponseModel{
    protected String status;
    protected String message;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
