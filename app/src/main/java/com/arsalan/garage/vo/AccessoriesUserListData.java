package com.arsalan.garage.vo;

import java.util.ArrayList;

import networking.models.ValueObject;

/**
 * <p/>
 * Created by: Noor  Alam on 14/05/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class AccessoriesUserListData extends UserBaseData implements ValueObject {

    private ArrayList<UserListItem> results;

    public ArrayList<UserListItem> getResults() {
        return results;
    }

    public void setResults(ArrayList<UserListItem> results) {
        this.results = results;
    }

    public static class AccessoriesUserItem extends UserListItem{
        private String accessories_id;

        public String getAccessories_id() {
            return accessories_id;
        }

        public void setAccessories_id(String accessories_id) {
            this.accessories_id = accessories_id;
            super.setId(accessories_id);
        }
    }
}
