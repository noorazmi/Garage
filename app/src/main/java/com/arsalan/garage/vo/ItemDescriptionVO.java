package com.arsalan.garage.vo;

import networking.models.ValueObject;

/**
 * <p/>
 * Created by: Noor  Alam on 10/09/15.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class ItemDescriptionVO extends BaseVO implements ValueObject {
    private Results results;


    public static class Results{
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

    public Results getResults() {
        return results;
    }

    public void setResults(Results results) {
        this.results = results;
    }
}


