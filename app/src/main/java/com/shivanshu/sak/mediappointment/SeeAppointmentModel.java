package com.shivanshu.sak.mediappointment;

/**
 * Created by shivanshu on 4/24/2017.
 */

public class SeeAppointmentModel {

    String name,patient_id;

    public SeeAppointmentModel(String name, String patient_id) {
        this.name = name;
        this.patient_id = patient_id;
    }

    public SeeAppointmentModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }
}
