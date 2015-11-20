package com.arsalan.garage.models;

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
    private String url;


    public HomeMenuItem(int imageId, String menuTitle, String menuType) {
        this.imageId = imageId;
        this.menuTitle = menuTitle;
        this.menuType = menuType;
    }
    public HomeMenuItem(int imageId, String menuTitle) {
        this.imageId = imageId;
        this.menuTitle = menuTitle;
    }

    public HomeMenuItem(int imageId, String menuTitle, String menuType, String url) {
        this.imageId = imageId;
        this.menuTitle = menuTitle;
        this.menuType = menuType;
        this.url = url;
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

    public String getUrl() {
        return url;
    }
}
