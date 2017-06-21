package com.shivanshu.sak.mediappointment;

/**
 * Created by shivanshu on 5/5/2017.
 */

public class MyAppointmentModel {

    private String dep_name,date,doc_id,status,time;

    public MyAppointmentModel() {
    }

    public MyAppointmentModel(String dep_name, String date, String doc_id, String status, String time) {
        this.dep_name = dep_name;
        this.date = date;
        this.doc_id = doc_id;
        this.status = status;
        this.time = time;
    }

    public String getDep_name() {
        return dep_name;
    }

    public void setDep_name(String dep_name) {
        this.dep_name = dep_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(String doc_id) {
        this.doc_id = doc_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
