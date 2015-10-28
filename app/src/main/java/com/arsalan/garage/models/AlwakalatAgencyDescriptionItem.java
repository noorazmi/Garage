package com.arsalan.garage.models;

/**
 * <p/>
 * Created by: Noor  Alam on 26/10/15.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class AlwakalatAgencyDescriptionItem {

    private int[] imageResIds;
    private String modelDescription1;
    private String modelDescription2;
    private String modelDescription3;

    private String offerDescription;

    public int[] getImageResIds() {
        return imageResIds;
    }

    public void setImageResIds(int[] imageResIds) {
        this.imageResIds = imageResIds;
    }

    public String getModelDescription1() {
        return modelDescription1;
    }

    public void setModelDescription1(String modelDescription1) {
        this.modelDescription1 = modelDescription1;
    }

    public String getModelDescription2() {
        return modelDescription2;
    }

    public void setModelDescription2(String modelDescription2) {
        this.modelDescription2 = modelDescription2;
    }

    public String getModelDescription3() {
        return modelDescription3;
    }

    public void setModelDescription3(String modelDescription3) {
        this.modelDescription3 = modelDescription3;
    }

    public String getOfferDescription() {
        return offerDescription;
    }

    public void setOfferDescription(String offerDescription1) {
        this.offerDescription = offerDescription1;
    }

}
