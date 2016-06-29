package com.arsalan.garage.vo;

import com.arsalan.garage.models.ImageInfo;

import java.util.ArrayList;

import networking.models.ValueObject;

/**
 * <p/>
 * Created by: Noor  Alam on 13/05/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class MarineUserDetailsData extends BaseVO implements ValueObject {

    private MarineUserDetails results;

    public MarineUserDetails getResults() {
        return results;
    }

    public static class MarineUserDetails{
        private String marine_id;
        private String make_region_name;
        private String title;
        private String phone;
        private String price;
        private String description;
        private ArrayList<ImageInfo> images;
        private int is_owner;
        private String model;

        public String getMarine_id() {
            return marine_id;
        }

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

}
