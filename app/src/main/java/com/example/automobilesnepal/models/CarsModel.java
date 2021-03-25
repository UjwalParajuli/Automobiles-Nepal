package com.example.automobilesnepal.models;

import java.io.Serializable;

public class CarsModel implements Serializable {
    private String brand_logo, car_image, car_name, car_brand, car_description, car_mileage, car_fuel_type, car_displacement, car_max_power, car_price, car_max_torque, car_seat_capacity, car_transmission_type, car_boot_space, car_fuel_capacity, car_body_type;
    private int car_model_id;

    public CarsModel(int car_model_id, String brand_logo, String car_image, String car_name, String car_brand, String car_description, String car_mileage, String car_fuel_type, String car_displacement, String car_max_power, String car_price, String car_max_torque, String car_seat_capacity, String car_transmission_type, String car_boot_space, String car_fuel_capacity, String car_body_type) {
        this.car_image = car_image;
        this.car_name = car_name;
        this.car_brand = car_brand;
        this.car_mileage = car_mileage;
        this.car_fuel_type = car_fuel_type;
        this.car_displacement = car_displacement;
        this.car_max_power = car_max_power;
        this.car_price = car_price;
        this.car_max_torque = car_max_torque;
        this.car_seat_capacity = car_seat_capacity;
        this.car_transmission_type = car_transmission_type;
        this.car_boot_space = car_boot_space;
        this.car_fuel_capacity = car_fuel_capacity;
        this.car_body_type = car_body_type;
        this.car_model_id = car_model_id;
        this.brand_logo = brand_logo;
        this.car_description = car_description;
    }

    public int getCar_model_id() {
        return car_model_id;
    }

    public void setCar_model_id(int car_model_id) {
        this.car_model_id = car_model_id;
    }

    public String getBrand_logo() {
        return brand_logo;
    }

    public void setBrand_logo(String brand_logo) {
        this.brand_logo = brand_logo;
    }

    public String getCar_image() {
        return car_image;
    }

    public void setCar_image(String car_image) {
        this.car_image = car_image;
    }

    public String getCar_name() {
        return car_name;
    }

    public void setCar_name(String car_name) {
        this.car_name = car_name;
    }

    public String getCar_brand() {
        return car_brand;
    }

    public void setCar_brand(String car_brand) {
        this.car_brand = car_brand;
    }

    public String getCar_mileage() {
        return car_mileage;
    }

    public void setCar_mileage(String car_mileage) {
        this.car_mileage = car_mileage;
    }

    public String getCar_fuel_type() {
        return car_fuel_type;
    }

    public void setCar_fuel_type(String car_fuel_type) {
        this.car_fuel_type = car_fuel_type;
    }

    public String getCar_displacement() {
        return car_displacement;
    }

    public void setCar_displacement(String car_displacement) {
        this.car_displacement = car_displacement;
    }

    public String getCar_max_power() {
        return car_max_power;
    }

    public void setCar_max_power(String car_max_power) {
        this.car_max_power = car_max_power;
    }

    public String getCar_price() {
        return car_price;
    }

    public void setCar_price(String car_price) {
        this.car_price = car_price;
    }

    public String getCar_max_torque() {
        return car_max_torque;
    }

    public void setCar_max_torque(String car_max_torque) {
        this.car_max_torque = car_max_torque;
    }

    public String getCar_seat_capacity() {
        return car_seat_capacity;
    }

    public void setCar_seat_capacity(String car_seat_capacity) {
        this.car_seat_capacity = car_seat_capacity;
    }

    public String getCar_transmission_type() {
        return car_transmission_type;
    }

    public void setCar_transmission_type(String car_transmission_type) {
        this.car_transmission_type = car_transmission_type;
    }

    public String getCar_boot_space() {
        return car_boot_space;
    }

    public void setCar_boot_space(String car_boot_space) {
        this.car_boot_space = car_boot_space;
    }

    public String getCar_fuel_capacity() {
        return car_fuel_capacity;
    }

    public void setCar_fuel_capacity(String car_fuel_capacity) {
        this.car_fuel_capacity = car_fuel_capacity;
    }

    public String getCar_body_type() {
        return car_body_type;
    }

    public void setCar_body_type(String car_body_type) {
        this.car_body_type = car_body_type;
    }

    public String getCar_description() {
        return car_description;
    }

    public void setCar_description(String car_description) {
        this.car_description = car_description;
    }
}
