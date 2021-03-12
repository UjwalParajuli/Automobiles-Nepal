package com.example.automobilesnepal.models;

import java.io.Serializable;

public class CarsModel implements Serializable {
    private String car_image, car_name, car_brand, car_engine, car_bhp, car_seat_capacity, car_mileage, car_price, car_fuel_type, car_tank_capacity, car_description;

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

    public String getCar_engine() {
        return car_engine;
    }

    public void setCar_engine(String car_engine) {
        this.car_engine = car_engine;
    }

    public String getCar_bhp() {
        return car_bhp;
    }

    public void setCar_bhp(String car_bhp) {
        this.car_bhp = car_bhp;
    }

    public String getCar_seat_capacity() {
        return car_seat_capacity;
    }

    public void setCar_seat_capacity(String car_seat_capacity) {
        this.car_seat_capacity = car_seat_capacity;
    }

    public String getCar_mileage() {
        return car_mileage;
    }

    public void setCar_mileage(String car_mileage) {
        this.car_mileage = car_mileage;
    }

    public String getCar_price() {
        return car_price;
    }

    public void setCar_price(String car_price) {
        this.car_price = car_price;
    }

    public String getCar_fuel_type() {
        return car_fuel_type;
    }

    public void setCar_fuel_type(String car_fuel_type) {
        this.car_fuel_type = car_fuel_type;
    }

    public String getCar_tank_capacity() {
        return car_tank_capacity;
    }

    public void setCar_tank_capacity(String car_tank_capacity) {
        this.car_tank_capacity = car_tank_capacity;
    }

    public String getCar_description() {
        return car_description;
    }

    public void setCar_description(String car_description) {
        this.car_description = car_description;
    }

    public CarsModel(String car_image, String car_name, String car_brand, String car_engine, String car_bhp, String car_seat_capacity, String car_mileage, String car_price, String car_fuel_type, String car_tank_capacity, String car_description) {
        this.car_image = car_image;
        this.car_name = car_name;
        this.car_brand = car_brand;
        this.car_engine = car_engine;
        this.car_bhp = car_bhp;
        this.car_seat_capacity = car_seat_capacity;
        this.car_mileage = car_mileage;
        this.car_price = car_price;
        this.car_fuel_type = car_fuel_type;
        this.car_tank_capacity = car_tank_capacity;
        this.car_description = car_description;
    }
}
