package com.arsalan.garage.models;

import com.arsalan.garage.vo.BaseVO;

import networking.models.ValueObject;

/**
 * <p/>
 * Created by: Noor  Alam on 04/07/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class AccessoriesUserDetailsData extends BaseVO implements ValueObject {

    private AccessoriesUserDetails results;

    public AccessoriesUserDetails getResults() {
        return results;
    }

    public static class AccessoriesUserDetails extends UserDetailsBase{
        private String accessories_id;

        public String getAccessories_id() {
            return accessories_id;
        }
    }

}