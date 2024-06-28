package org.example.curs_bd;
public class Cars {
    private String license_plate;
    private String model;
    private String brand;
    private String color;
    private int mileage;

    public Cars(String license_plate, String model, String brand, String color, int mileage) {
        this.license_plate = license_plate;
        this.model = model;
        this.brand = brand;
        this.color = color;
        this.mileage = mileage;
    }

    // Геттер для license_plate
    public String getLicense_plate() {
        return license_plate;
    }

    // Сеттер для license_plate (если нужен)
    public void setLicense_plate(String license_plate) {
        this.license_plate = license_plate;
    }

    // Геттер для model
    public String getModel() {
        return model;
    }

    // Сеттер для model (если нужен)
    public void setModel(String model) {
        this.model = model;
    }

    // Геттер для brand
    public String getBrand() {
        return brand;
    }

    // Сеттер для brand (если нужен)
    public void setBrand(String brand) {
        this.brand = brand;
    }

    // Геттер для color
    public String getColor() {
        return color;
    }

    // Сеттер для color (если нужен)
    public void setColor(String color) {
        this.color = color;
    }

    // Геттер для mileage
    public int getMileage() {
        return mileage;
    }

    // Сеттер для mileage (если нужен)
    public void setMileage(int mileage) {
        this.mileage = mileage;
    }
}

