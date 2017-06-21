package com.shivanshu.sak.mediappointment;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DepartmentTreatments extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private TextView about;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_treatments);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_department);
        toolbar.setTitle("Toolbar Title");

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar_department);
        collapsingToolbar.setTitle("DepartmentsInfo");

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://mediappointment-9acf4.firebaseio.com/Departments/Cardiology/about_dep");

        about=(TextView)findViewById(R.id.department_description);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value=dataSnapshot.getValue(String.class);
                about.setText(value);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        Firebase.setAndroidContext(this);
    }
}
