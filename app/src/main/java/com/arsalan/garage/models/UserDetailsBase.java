package com.arsalan.garage.models;

import java.util.ArrayList;

/**
 * <p/>
 * Created by: Noor  Alam on 14/07/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class UserDetailsBase {
    private String make_region_name;
    private String title;
    private String phone;
    private String price;
    private String description;
    private ArrayList<ImageInfo> images;
    private int is_owner;
    private String model;

    public String getMake_region_name() {
        return make_region_name;
    }

    public String getTitle() {
        return title;
    }

    public String getPhone() {
        return phone;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<ImageInfo> getImages() {
        return images;
    }

    public int getIs_owner() {
        return is_owner;
    }

    public String getModel() {
        return model;
    }

}
