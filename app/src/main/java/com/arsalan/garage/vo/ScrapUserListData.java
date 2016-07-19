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
public class ScrapUserListData extends UserBaseData implements ValueObject {

    private ArrayList<UserListItem> results;

    public ArrayList<UserListItem> getResults() {
        return results;
    }

    public void setResults(ArrayList<UserListItem> results) {
        this.results = results;
    }

    public static class ScrapUserListItem extends UserListItem {
        private String scrap_id;

        public String getScrap_id() {
            return scrap_id;
        }

        public void setScrap_id(String scrap_id) {
            this.scrap_id = scrap_id;
            setId(scrap_id);
        }
    }
}
