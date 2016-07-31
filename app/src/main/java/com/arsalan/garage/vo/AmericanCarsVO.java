package com.arsalan.garage.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import networking.models.ValueObject;

/**
 * <p/>
 * Created by: Noor  Alam on 03/09/15.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */

public class AmericanCarsVO extends BaseVO implements ValueObject {


    private Integer dataCount;
    private Integer data_count;
    private List<Result> results = new ArrayList<Result>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    public Integer getDataCount() {
        return dataCount;
    }


    public void setDataCount(Integer dataCount) {
        this.dataCount = dataCount;
    }


    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public Integer getData_count() {
        return data_count;
    }

    public static class Result {

        private String item_id;
        private String phone;
        private String description;
        private String image;

        public String getItem_id() {
            return item_id;
        }

        public void setItem_id(String item_id) {
            this.item_id = item_id;
        }

        public String getPhone() {
            return phone;
        }


        public void setPhone(String phone) {
            this.phone = phone;
        }


        public String getDescription() {
            return description;
        }


        public void setDescription(String description) {
            this.description = description;
        }


        public String getImage() {
            return image;
        }


        public void setImage(String image) {
            this.image = image;
        }


    }
}
