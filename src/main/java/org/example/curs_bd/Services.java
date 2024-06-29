package org.example.curs_bd;

import java.sql.Date;

public class Services {
    private Date date_begin;
    private Date date_end;
    private double hours_worked;
    private int car_id;
    private String category;

    public Services(Date date_begin, Date date_end, double hours_worked, int car_id, String category) {
        this.date_begin = date_begin;
        this.date_end = date_end;
        this.hours_worked = hours_worked;
        this.car_id = car_id;
        this.category = category;
    }

    public Date getDate_begin() {
        return date_begin;
    }

    public void setDate_begin(Date date_begin) {
        this.date_begin = date_begin;
    }

    public Date getDate_end() {
        return date_end;
    }

    public void setDate_end(Date date_end) {
        this.date_end = date_end;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
