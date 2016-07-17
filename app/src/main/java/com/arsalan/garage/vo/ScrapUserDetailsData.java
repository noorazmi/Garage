package com.arsalan.garage.vo;

import com.arsalan.garage.models.UserDetailsBase;

import networking.models.ValueObject;

/**
 * <p/>
 * Created by: Noor  Alam on 14/05/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class ScrapUserDetailsData extends BaseVO implements ValueObject {

    private ScrapUserDetails results;

    public ScrapUserDetails getResults() {
        return results;
    }

    public static class ScrapUserDetails extends UserDetailsBase {
        private String scrap_id;

        public String getScrap_id() {
            return scrap_id;
        }

    }

}
