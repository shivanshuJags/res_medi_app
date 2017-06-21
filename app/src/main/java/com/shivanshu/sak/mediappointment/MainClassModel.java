package com.shivanshu.sak.mediappointment;

/**
 * Created by shivanshu on 3/12/2017.
 */

public class MainClassModel {

    private String dep_name;
    private String dep_image;

    public MainClassModel() {
    }

    public MainClassModel(String dep_name, String dep_image) {
        this.dep_name = dep_name;
        this.dep_image = dep_image;
    }

    public String getDep_name() {
        return dep_name;
    }

    public void setDep_name(String dep_name) {
        this.dep_name = dep_name;
    }

    public String getDep_image() {
        return dep_image;
    }

    public void setDep_image(String dep_image) {
        this.dep_image = dep_image;
    }
}
