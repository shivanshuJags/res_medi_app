package com.shivanshu.sak.mediappointment;

/**
 * Created by shivanshu on 3/4/2017.
 */

public class ModelClass {

    private String doc_name,doc_id,doc_fee,status;

    public ModelClass() {
    }


  /*public ModelClass(String doc_name) {
        this.doc_name = doc_name;
    }*/

    public ModelClass(String doc_name, String doc_id, String doc_fee, String status) {
        this.doc_name = doc_name;
        this.doc_id = doc_id;
        this.doc_fee = doc_fee;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDoc_name() {
        return doc_name;
    }

    public void setDoc_name(String doc_name) {
        this.doc_name = doc_name;
    }

    public String getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(String doc_id) {
        this.doc_id = doc_id;
    }

    public String getDoc_fee() {
        return doc_fee;
    }

    public void setDoc_fee(String doc_fee) {
        this.doc_fee = doc_fee;
    }
}
