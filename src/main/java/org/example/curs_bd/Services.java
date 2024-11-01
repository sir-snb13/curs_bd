package org.example.curs_bd;

import java.sql.Date;

public class Services {
    private int service_id;
    private Date start_date;
    private Date end_date;
    private double hours_worked;
    private int car_id;
    private int employee_id;
    private String category;

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public double getHours_worked() {
        return hours_worked;
    }

    public void setHours_worked(double hours_worked) {
        this.hours_worked = hours_worked;
    }

    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Services(int service_id, Date start_date, Date end_date, double hours_worked, int car_id, String category) {
        this.service_id = service_id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.hours_worked = hours_worked;
        this.car_id = car_id;
        this.category = category;
    }
}
