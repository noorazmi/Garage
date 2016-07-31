package com.arsalan.garage.vo;

import com.arsalan.garage.models.UserDetailsBase;

import networking.models.ValueObject;

/**
 * <p/>
 * Created by: Noor  Alam on 01/08/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class UserDetailsData extends BaseVO implements ValueObject {

    private UserDetails results;

    public UserDetails getResults() {
        return results;
    }

    public static class UserDetails extends UserDetailsBase {
    }

}