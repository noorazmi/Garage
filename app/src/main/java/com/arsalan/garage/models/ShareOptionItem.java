package com.arsalan.garage.models;

/**
 * <p/>
 * Created by: Noor  Alam on 11/06/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class ShareOptionItem {

    private int title;
    private int imageIcon;

    public ShareOptionItem(int title, int imageIcon) {
        this.title = title;
        this.imageIcon = imageIcon;
    }

    public int getTitle() {
        return title;
    }

    public int getImageIcon() {
        return imageIcon;
    }
}
