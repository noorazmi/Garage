package com.arsalan.garage.vo;

import java.util.ArrayList;

import networking.models.ValueObject;

/**
 * <p/>
 * Created by: Noor  Alam on 06/08/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class CarWashVO  extends BaseVO implements ValueObject {

    private int data_count;
    private ArrayList<CarWash> results;


    public int getData_count() {
        return data_count;
    }

    public void setData_count(int data_count) {
        this.data_count = data_count;
    }

    public ArrayList<CarWash> getResults() {
        return results;
    }

    public void setResults(ArrayList<CarWash> results) {
        this.results = results;
    }

    public static class CarWash{
        private String car_wash_id;
        private String name;
        private String description;
        private String[] phone;
        private String image;

        public String getCar_wash_id() {
            return car_wash_id;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String[] getPhone() {
            return phone;
        }

        public String getImage() {
            return image;
        }
    }
}
