package com.example.automobilesnepal.models;

import java.io.Serializable;

public class AccessoriesModel implements Serializable {
    private int accessory_id, available_quantity;
    private double accessory_price;
    private String title, type, description, vehicle, image;

    public AccessoriesModel(int accessory_id, int available_quantity, double accessory_price, String title, String type, String description, String vehicle, String image) {
        this.accessory_id = accessory_id;
        this.available_quantity = available_quantity;
        this.accessory_price = accessory_price;
        this.title = title;
        this.type = type;
        this.description = description;
        this.vehicle = vehicle;
        this.image = image;
    }

    public int getAccessory_id() {
        return accessory_id;
    }

    public void setAccessory_id(int accessory_id) {
        this.accessory_id = accessory_id;
    }

    public int getAvailable_quantity() {
        return available_quantity;
    }

    public void setAvailable_quantity(int available_quantity) {
        this.available_quantity = available_quantity;
    }

    public double getAccessory_price() {
        return accessory_price;
    }

    public void setAccessory_price(double accessory_price) {
        this.accessory_price = accessory_price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
