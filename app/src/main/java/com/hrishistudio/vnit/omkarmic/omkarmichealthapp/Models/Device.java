package com.hrishistudio.vnit.omkarmic.omkarmichealthapp.Models;

import java.util.ArrayList;

public class Device {
    private String name;
    private String image;
    private String model;
    private ArrayList<String> parameters;

    public Device() {

    }

    public Device(String name, String image, String model, ArrayList<String> parameters) {
        this.name = name;
        this.image = image;
        this.model = model;
        this.parameters = parameters;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<String> getParameters() {
        return parameters;
    }

    public void setParameters(ArrayList<String> parameters) {
        this.parameters = parameters;
    }
}
