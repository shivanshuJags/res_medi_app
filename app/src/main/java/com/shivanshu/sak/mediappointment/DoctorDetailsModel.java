package com.shivanshu.sak.mediappointment;

/**
 * Created by shivanshu on 3/11/2017.
 */

public class DoctorDetailsModel {

    private String doc_name;
    private String dr_degree;
    private String dr_experience;
    private String dr_description;
    private String doc_image;

    public DoctorDetailsModel() {
    }

    public String getDoc_image() {
        return doc_image;
    }

    public void setDoc_image(String doc_image) {
        this.doc_image = doc_image;
    }

    public String getDoc_name() {
        return doc_name;
    }

    public void setDoc_name(String doc_name) {
        this.doc_name = doc_name;
    }

    public String getDr_degree() {
        return dr_degree;
    }

    public void setDr_degree(String dr_degree) {
        this.dr_degree = dr_degree;
    }

    public String getDr_experience() {
        return dr_experience;
    }

    public void setDr_experience(String dr_experience) {
        this.dr_experience = dr_experience;
    }

    public String getDr_description() {
        return dr_description;
    }

    public void setDr_description(String dr_description) {
        this.dr_description = dr_description;
    }

    public DoctorDetailsModel(String doc_name, String dr_description, String dr_experience, String dr_degree, String doc_image) {
        this.doc_name = doc_name;
        this.dr_description = dr_description;
        this.dr_experience = dr_experience;
        this.dr_degree = dr_degree;
        this.doc_image=doc_image;

    }
}
