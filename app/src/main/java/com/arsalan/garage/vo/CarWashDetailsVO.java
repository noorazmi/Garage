package com.arsalan.garage.vo;

import com.arsalan.garage.models.ImageInfo;

import java.util.ArrayList;
import java.util.List;

import networking.models.ValueObject;

/**
 * <p/>
 * Created by: Noor  Alam on 06/08/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class CarWashDetailsVO extends BaseVO implements ValueObject {

    private Results results;

    public Results getResults() {
        return results;
    }

    public void setResults(Results results) {
        this.results = results;
    }

    public static class Results {
        public int carWashId;
        public String name;
        public String description;
        public List<String> phone = new ArrayList<String>();
        public ArrayList<ImageInfo> images = new ArrayList<ImageInfo>();

        public int getCarWashId() {
            return carWashId;
        }

        public void setCarWashId(int carWashId) {
            this.carWashId = carWashId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<String> getPhone() {
            return phone;
        }

        public void setPhone(List<String> phone) {
            this.phone = phone;
        }

        public ArrayList<ImageInfo> getImages() {
            return images;
        }

        public void setImages(ArrayList<ImageInfo> images) {
            this.images = images;
        }
    }
}