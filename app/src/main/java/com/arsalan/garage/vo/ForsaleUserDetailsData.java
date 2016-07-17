package com.arsalan.garage.vo;

import com.arsalan.garage.models.UserDetailsBase;

import networking.models.ValueObject;

/**
 * <p/>
 * Created by: Noor  Alam on 28/11/15.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class ForSaleUserDetailsData extends BaseVO implements ValueObject {

    private ForSaleUserDetails results;

    public ForSaleUserDetails getResults() {
        return results;
    }

    public static class ForSaleUserDetails extends UserDetailsBase{
        private String forsale_id;

        public String getForsale_id() {
            return forsale_id;
        }
    }
}