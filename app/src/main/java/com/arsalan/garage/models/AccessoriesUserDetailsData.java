package com.arsalan.garage.models;

import com.arsalan.garage.vo.BaseVO;

import java.util.ArrayList;

import networking.models.ValueObject;

/**
 * <p/>
 * Created by: Noor  Alam on 04/07/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class AccessoriesUserDetailsData extends BaseVO implements ValueObject {

    private AccessoriesUserDetails results;

    public AccessoriesUserDetails getResults() {
        return results;
    }

    public static class AccessoriesUserDetails{
        private String accessories_id;
        private String make_region_name;
        private String title;
        private String phone;
        private String price;
        private String description;
        private ArrayList<ImageInfo> images;
        private int is_owner;
        private String model;

        public String getAccessories_id() {
            return accessories_id;
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