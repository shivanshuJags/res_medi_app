package com.shivanshu.sak.mediappointment;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class FreeClinic extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_clinic);

        Toolbar toolbar = (Toolbar) findViewById(R.id.dr_after_toolbar);
        toolbar.setTitle("Toolbar Title");

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
        collapsingToolbar.setTitle("Free Clinics");

    }

    }

