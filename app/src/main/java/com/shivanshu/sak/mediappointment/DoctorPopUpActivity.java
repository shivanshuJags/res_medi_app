package com.shivanshu.sak.mediappointment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class DoctorPopUpActivity extends AppCompatActivity {

    ListView listView ;
    String  dep_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_pop_up);

        listView = (ListView) findViewById(R.id.listView);

        list_setting();


        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        getWindow().setLayout((int)(width*0.8),(int)(height*0.6));
    }

    public void list_setting()
    {
        String[] values = new String[] { "Cardiology",
                "Gastrosurgery",
                "Neurology",
                "Neurosurggery",
                "Radiation Oncology",
                "Urology",
                "Dentist",

        };

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(
                this,android.R.layout.simple_list_item_1,android.R.id.text1,values
        );

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                 dep_name = (String) listView.getItemAtPosition(position);

                Intent i= new Intent(DoctorPopUpActivity.this,AfterDoctorLogin.class);
                i.putExtra("dep_name",dep_name);
                startActivity(i);

            }
        });

    }


}
