package com.arsalan.garage.vo;

import java.util.ArrayList;

import networking.models.ValueObject;

/**
 * <p/>
 * Created by: Noor  Alam on 28/11/15.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class BuyAndSaleListItemVo extends BaseVO implements ValueObject {

    private int data_count;
    ArrayList<BuySaleItem> results;

    public int getData_count() {
        return data_count;
    }

    public ArrayList<BuySaleItem> getResults() {
        return results;
    }

    public static class BuySaleItem{
        String forsale_id;
        String make;
        String make_region_name;
        String title;
        String phone;
        String price;
        String description;
        String image;

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

        public String getImage() {
            return image;
        }
    }
}
