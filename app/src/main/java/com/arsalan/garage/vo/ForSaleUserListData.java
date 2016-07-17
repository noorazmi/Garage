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
public class ForSaleUserListData extends UserBaseData implements ValueObject {

    ArrayList<UserListItem> results;


    public ArrayList<UserListItem> getResults() {
        return results;
    }

    public void setResults(ArrayList<UserListItem> results) {
        this.results = results;
    }

    public static class ForSaleItem extends UserListItem {
        String forsale_id;

        public String getForsale_id() {
            return forsale_id;
        }

        public void setForsale_id(String forsale_id) {
            this.forsale_id = forsale_id;
            setId(forsale_id);
        }
    }
}
