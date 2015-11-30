package com.arsalan.garage.vo;

import com.arsalan.garage.models.ImageInfo;

import java.util.ArrayList;

import networking.models.ValueObject;

/**
 * <p/>
 * Created by: Noor  Alam on 28/11/15.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class BuyAndSaleDescVo extends BaseVO implements ValueObject {

    private Results results;

    public Results getResults() {
        return results;
    }

    public static class Results {
        String forsale_id;
        String make;
        String make_region_name;
        String title;
        String phone;
        String price;
        String description;
        ArrayList<ImageInfo> images;

        public String getForsale_id() {
            return forsale_id;
        }

        public String getMake() {
            return make;
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

//    public static class BuySaleImage{
//        String photo_id;
//        String photo_name;
//        String default_value;
//
//        public String getPhoto_id() {
//            return photo_id;
//        }
//
//        public String getPhoto_name() {
//            return photo_name;
//        }
//
//        public String getDefault_value() {
//            return default_value;
//        }
//    }


}