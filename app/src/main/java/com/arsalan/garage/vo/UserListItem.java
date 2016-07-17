package com.arsalan.garage.vo;

/**
 * <p/>
 * Created by: Noor  Alam on 16/07/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class UserListItem {
    private String make;
    private String make_region_name;
    private String title;
    private String phone;
    private String price;
    private String description;
    private String image;
    private String id;
    private String post_date;
    private int is_owner;

    public String getMake() {
        return make;
    }

    public String getMake_region_name() {
        return make_region_name;
    }

    public String getTitle() {
        return title;
    }

    public String getPhone() {
        return phone;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPost_date() {
        return post_date;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setMake_region_name(String make_region_name) {
        this.make_region_name = make_region_name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPost_date(String post_date) {
        this.post_date = post_date;
    }

    public int getIs_owner() {
        return is_owner;
    }

    public void setIs_owner(int is_owner) {
        this.is_owner = is_owner;
    }
}
