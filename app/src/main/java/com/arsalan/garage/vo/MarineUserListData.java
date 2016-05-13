package com.arsalan.garage.vo;

import java.util.ArrayList;

import networking.models.ValueObject;

/**
 * <p/>
 * Created by: Noor  Alam on 13/05/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class MarineUserListData extends BaseVO implements ValueObject {

    private String data_count;
    private ArrayList<MarineUserItem> results;

    public String getData_count() {
        return data_count;
    }

    public ArrayList<MarineUserItem> getResults() {
        return results;
    }

    public static class MarineUserItem{
       private String marine_id;
        private String make_region_name;
        private String title;
        private String phone;
        private String price;
        private String description;
        private String image;

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

        public String getImage() {
            return image;
        }
    }
}
