package com.arsalan.garage.models;

import com.arsalan.garage.utils.AppConstants;

/**
 * <p/>
 * Project: <b>Loud Shout</b><br/>
 * Created by: Noor  Alam on 06/08/15.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class HomeMenuItem {
    private int imageId;
    private String menuTitle;
    private String menuType;


    public HomeMenuItem(int imageId, String menuTitle, String menuType) {
        this.imageId = imageId;
        this.menuTitle = menuTitle;
        this.menuType = menuType;
    }
    public HomeMenuItem(int imageId, String menuTitle) {
        this(imageId, menuTitle, AppConstants.SCREEN_TYPE);
    }

    public int getImageId() {
        return imageId;
    }

    public String getMenuTitle() {
        return menuTitle;
    }

    public String getMenuType(){
        return menuType;
    }

}
