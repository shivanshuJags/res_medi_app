package com.shivanshu.sak.mediappointment;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class MyAppointmentBookedStatus extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appointment_booked_status);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_app_status);
        toolbar.setTitle("My Appointment");

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar_app_status);
        collapsingToolbar.setTitle("Booked Appointments");
    }
}
