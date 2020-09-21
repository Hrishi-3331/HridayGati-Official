package com.hrishistudio.vnit.omkarmic.omkarmichealthapp.Models;

public class Parameter {
    private String name;
    private double normal_value;
    private double last_value;
    private String detail;

    public Parameter() {

    }

    public Parameter(String name, double normal_value, double last_value, String detail) {
        this.name = name;
        this.normal_value = normal_value;
        this.last_value = last_value;
        this.detail = detail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getNormal_value() {
        return normal_value;
    }

    public void setNormal_value(double normal_value) {
        this.normal_value = normal_value;
    }

    public double getLast_value() {
        return last_value;
    }

    public void setLast_value(double last_value) {
        this.last_value = last_value;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
