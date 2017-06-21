package com.shivanshu.sak.mediappointment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DoctorOrPatient extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_or_patient);

    }

    public void patient(View v)
    {
        startActivity(new Intent(DoctorOrPatient.this,MainClass.class));

    }
    public void doctor(View v)
    {

        startActivity(new Intent(DoctorOrPatient.this,DoctorPopUpActivity.class));
    }
}
