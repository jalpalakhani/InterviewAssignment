package com.example.rides.responseModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class RandomVehicleResponse {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("vin")
    @Expose
    private String vin;
    @SerializedName("make_and_model")
    @Expose
    private String makeAndModel;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("transmission")
    @Expose
    private String transmission;
    @SerializedName("drive_type")
    @Expose
    private String driveType;
    @SerializedName("fuel_type")
    @Expose
    private String fuelType;
    @SerializedName("car_type")
    @Expose
    private String carType;
    @SerializedName("car_options")
    @Expose
    private ArrayList<String> carOptions = null;
    @SerializedName("specs")
    @Expose
    private ArrayList<String> specs = null;
    @SerializedName("doors")
    @Expose
    private int doors;
    @SerializedName("mileage")
    @Expose
    private int mileage;
    @SerializedName("kilometrage")
    @Expose
    private int kilometrage;
    @SerializedName("license_plate")
    @Expose
    private String licensePlate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getMakeAndModel() {
        return makeAndModel;
    }

    public void setMakeAndModel(String makeAndModel) {
        this.makeAndModel = makeAndModel;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getDriveType() {
        return driveType;
    }

    public void setDriveType(String driveType) {
        this.driveType = driveType;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public ArrayList<String> getCarOptions() {
        return carOptions;
    }

    public void setCarOptions(ArrayList<String> carOptions) {
        this.carOptions = carOptions;
    }

    public ArrayList<String> getSpecs() {
        return specs;
    }

    public void setSpecs(ArrayList<String> specs) {
        this.specs = specs;
    }

    public int getDoors() {
        return doors;
    }

    public void setDoors(int doors) {
        this.doors = doors;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public int getKilometrage() {
        return kilometrage;
    }

    public void setKilometrage(int kilometrage) {
        this.kilometrage = kilometrage;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

}
