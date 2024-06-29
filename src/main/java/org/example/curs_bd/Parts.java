package org.example.curs_bd;

public class Parts {
    private int part_id;
    private String category;
    private double price;
    private String model;
    private String serial_number;
    private int service_id;

    public Parts(String category, double price, String model, String serial_number, int service_id) {
        this.category = category;
        this.price = price;
        this.model = model;
        this.serial_number = serial_number;
        this.service_id = service_id;
    }

    // Геттеры и сеттеры
    public int getPart_id() {
        return part_id;
    }

    public void setPart_id(int part_id) {
        this.part_id = part_id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }
}
