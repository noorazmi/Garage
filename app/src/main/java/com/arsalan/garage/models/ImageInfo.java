package com.arsalan.garage.models;

/**
 * <p/>
 * Created by: Noor  Alam on 28/11/15.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class ImageInfo {
    String photo_id;
    String photo_name;
    String default_value;

    public String getPhoto_id() {
        return photo_id;
    }

    public String getPhoto_name() {
        return photo_name;
    }

    public String getDefault_value() {
        return default_value;
    }

    public void setPhoto_id(String photo_id) {
        this.photo_id = photo_id;
    }

    public void setPhoto_name(String photo_name) {
        this.photo_name = photo_name;
    }

    public void setDefault_value(String default_value) {
        this.default_value = default_value;
    }
}
