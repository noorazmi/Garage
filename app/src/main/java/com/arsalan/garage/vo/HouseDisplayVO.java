package com.arsalan.garage.vo;

import java.util.ArrayList;

import networking.models.ValueObject;

/**
 * <p/>
 * Created by: Noor  Alam on 19/11/15.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class HouseDisplayVo extends BaseVO implements ValueObject {

    private int data_count;
    private ArrayList<CarModel> results;


    public int getData_count() {
        return data_count;
    }

    public void setData_count(int data_count) {
        this.data_count = data_count;
    }

    public ArrayList<CarModel> getResults() {
        return results;
    }

    public void setResults(ArrayList<CarModel> results) {
        this.results = results;
    }

    public static class CarModel{
        private String showroom_car_id;
        private String make;
        private String model;
        private String year;
        private String engine;
        private String transmission;
        private String payment;
        private String price;
        private String description;
        private String contact;
        private String working_hours;
        private String image;
        private String hasChild;

        public String getShowroom_car_id() {
            return showroom_car_id;
        }

        public void setShowroom_car_id(String showroom_car_id) {
            this.showroom_car_id = showroom_car_id;
        }

        public String getMake() {
            return make;
        }

        public void setMake(String make) {
            this.make = make;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getEngine() {
            return engine;
        }

        public void setEngine(String engine) {
            this.engine = engine;
        }

        public String getTransmission() {
            return transmission;
        }

        public void setTransmission(String transmission) {
            this.transmission = transmission;
        }

        public String getPayment() {
            return payment;
        }

        public void setPayment(String payment) {
            this.payment = payment;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getWorking_hours() {
            return working_hours;
        }

        public void setWorking_hours(String working_hours) {
            this.working_hours = working_hours;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getHasChild() {
            return hasChild;
        }
    }


}
