package com.arsalan.garage.vo;

import com.arsalan.garage.models.ImageInfo;

import java.util.ArrayList;

import networking.models.ValueObject;

/**
 * <p/>
 * Created by: Noor  Alam on 14/05/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class ScrapUserDetailsData extends BaseVO implements ValueObject {

    private ScrapUserDetails results;

    public ScrapUserDetails getResults() {
        return results;
    }

    public static class ScrapUserDetails{
        private String marine_id;
        private String make_region_name;
        private String title;
        private String phone;
        private String price;
        private String description;
        private ArrayList<ImageInfo> images;

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
    }

}
