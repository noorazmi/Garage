package com.arsalan.garage.vo;

import com.arsalan.garage.models.ImageInfo;

import java.util.ArrayList;

import networking.models.ValueObject;

/**
 * <p/>
 * Created by: Noor  Alam on 08/08/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class MarineBoatFishingDetailsVO extends BaseVO implements ValueObject {

    private Results results;

    public Results getResults() {
        return results;
    }

    public void setResults(Results results) {
        this.results = results;
    }

    public static class Results {
        public int boat_fishing_id;
        public String description;
        public String phone;
        public String contact;

        public ArrayList<ImageInfo> images = new ArrayList<ImageInfo>();

        public int getBoat_fishing_id() {
            return boat_fishing_id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPhone() {
            return phone;
        }

        public ArrayList<ImageInfo> getImages() {
            return images;
        }

        public String getContact() {
            return contact;
        }
    }
}
