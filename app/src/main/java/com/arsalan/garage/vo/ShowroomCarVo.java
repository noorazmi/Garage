package com.arsalan.garage.vo;

import com.arsalan.garage.models.ImageInfo;

import java.util.ArrayList;

import networking.models.ValueObject;

/**
 * <p/>
 * Created by: Noor  Alam on 19/11/15.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class ShowroomCarVo extends BaseVO implements ValueObject {

    private Result results;

    public Result getResults() {
        return results;
    }

    public void setResults(Result results) {
        this.results = results;
    }

    public static class Result{
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
        private ArrayList<ImageInfo> images;

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

//        public ArrayList<CarImage> getImages() {
//            return images;
//        }
//
//        public void setImages(ArrayList<CarImage> images) {
//            this.images = images;
//        }

        public ArrayList<ImageInfo> getImages() {
            return images;
        }

        public void setImages(ArrayList<ImageInfo> images) {
            this.images = images;
        }
    }

//    public static class CarImage {
//        private String photo_id;
//        private String photo_name;
//
//        public String getPhoto_id() {
//            return photo_id;
//        }
//
//        public void setPhoto_id(String photo_id) {
//            this.photo_id = photo_id;
//        }
//
//        public String getPhoto_name() {
//            return photo_name;
//        }
//
//        public void setPhoto_name(String photo_name) {
//            this.photo_name = photo_name;
//        }
//    }
}
