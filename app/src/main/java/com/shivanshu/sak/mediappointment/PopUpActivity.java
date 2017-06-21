package com.shivanshu.sak.mediappointment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PopUpActivity extends AppCompatActivity {
    String date,time,dep_name;
    int intent_id=0;
    TextView txtdate,txttime;
    Button btnbook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);

        txtdate=(TextView)findViewById(R.id.date) ;
        txttime=(TextView)findViewById(R.id.time) ;
        btnbook=(Button)findViewById(R.id.book);

        //Setting Pop Up Window
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        getWindow().setLayout((int)(width*0.8),(int)(height*0.6));


        Intent();
        btnlistner();


    }

    public void Intent()
    {
        Intent i=getIntent();
        date= i.getStringExtra("date");
        time=i.getStringExtra("time");
        intent_id=i.getIntExtra("id",0);
        dep_name=i.getStringExtra("dep_name");
        Toast.makeText(PopUpActivity.this,"Item-Name ="+dep_name,Toast.LENGTH_LONG).show();

        txtdate.setText(date);
        txttime.setText(time);
    }

    public void btnlistner()
    {
        btnbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(PopUpActivity.this,DoctorsList.class);
                i.putExtra("id",intent_id);
                i.putExtra("date",date);
                i.putExtra("time",time);
                i.putExtra("dep_name",dep_name);
                startActivity(i);

            }
        });
    }
}
