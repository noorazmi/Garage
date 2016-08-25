package com.arsalan.garage.models;

import com.arsalan.garage.vo.BaseVO;

import java.util.ArrayList;

import networking.models.ValueObject;

/**
 * <p/>
 * Created by: Noor  Alam on 07/08/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class BoatFishingListVO extends BaseVO implements ValueObject {

    private int data_count;
    private ArrayList<BoatFishing> results;
    public int getData_count() {
        return data_count;
    }

    public ArrayList<BoatFishing> getResults() {
        return results;
    }

    public void setResults(ArrayList<BoatFishing> results) {
        this.results = results;
    }

    public static class BoatFishing{
        private String boat_fishing_id;
        private String name;
        private String description;
        private String phone;
        private String image;
        private String contact;

        public String getBoat_fishing_id() {
            return boat_fishing_id;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String getPhone() {
            return phone;
        }

        public String getImage() {
            return image;
        }

        public String getContact() {
            return contact;
        }
    }
}
