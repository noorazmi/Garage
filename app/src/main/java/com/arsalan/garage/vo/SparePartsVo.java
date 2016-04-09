package com.arsalan.garage.vo;

import java.util.ArrayList;

import networking.models.ValueObject;

/**
 * <p/>
 * Created by: Noor  Alam on 08/04/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class SparePartsVo extends BaseVO implements ValueObject {
    private int data_count;
    private ArrayList<SparePart> results;


    public int getData_count() {
        return data_count;
    }

    public ArrayList<SparePart> getResults() {
        return results;
    }

    public static class SparePart {
        String spare_part_id;
        String branch_name;
        String phone;
        String internal_phone;
        String address;
        String working_time;
        String make;

        public String getSpare_part_id() {
            return spare_part_id;
        }

        public String getBranch_name() {
            return branch_name;
        }

        public String getPhone() {
            return phone;
        }

        public String getInternal_phone() {
            return internal_phone;
        }

        public String getAddress() {
            return address;
        }

        public String getWorking_time() {
            return working_time;
        }

        public String getMake() {
            return make;
        }
    }
}
