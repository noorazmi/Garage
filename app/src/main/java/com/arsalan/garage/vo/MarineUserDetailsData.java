package com.arsalan.garage.vo;

import com.arsalan.garage.models.UserDetailsBase;

import networking.models.ValueObject;

/**
 * <p/>
 * Created by: Noor  Alam on 13/05/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class MarineUserDetailsData extends BaseVO implements ValueObject {

    private MarineUserDetails results;

    public MarineUserDetails getResults() {
        return results;
    }

    public static class MarineUserDetails extends UserDetailsBase{
        private String marine_id;
        public String getMarine_id() {
            return marine_id;
        }
    }

}
