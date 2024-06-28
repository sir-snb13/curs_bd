package org.example.curs_bd;
public class CarParts {
    private String service_name;
    private String parts;
    private int car_mileage;

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getParts() {
        return parts;
    }

    public void setParts(String parts) {
        this.parts = parts;
    }

    public int getCar_mileage() {
        return car_mileage;
    }

    public void setCar_mileage(int car_mileage) {
        this.car_mileage = car_mileage;
    }

    public String getCar_model() {
        return car_model;
    }

    public void setCar_model(String car_model) {
        this.car_model = car_model;
    }

    public int getTotal_parts_cost() {
        return total_parts_cost;
    }

    public void setTotal_parts_cost(int total_parts_cost) {
        this.total_parts_cost = total_parts_cost;
    }

    private String car_model;
    private int total_parts_cost;

    public CarParts(String service_name, String parts, int car_mileage, String car_model, int total_parts_cost) {
        this.service_name = service_name;
        this.parts = parts;
        this.car_mileage = car_mileage;
        this.car_model = car_model;
        this.total_parts_cost = total_parts_cost;
    }

}

