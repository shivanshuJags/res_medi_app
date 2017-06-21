package com.shivanshu.sak.mediappointment;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DoctorsList extends AppCompatActivity {


    private DatabaseReference databaseReference;
    private RecyclerView recyclerView_doc;
    int intent_id=0;
    String date,time,dep_name ,doc_id_,doc_fee;
    TextView appointment_date,appointment_time,appointment_doc_name;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_list);

        appointment_date=(TextView) findViewById(R.id.orignal_date);
        appointment_time=(TextView) findViewById(R.id.orignal_time);
        appointment_doc_name=(TextView) findViewById(R.id.orignal_doc_name);
       // proceed=(Button) findViewById(R.id.pay_doc) ;

        /*firebaseAuth=FirebaseAuth.getInstance();
        user_id= firebaseAuth.getCurrentUser().getEmail();
        user= user_id.split("@",2)[0];
*/
        Intent i=getIntent();
        intent_id=i.getIntExtra("id",0);
        date=i.getStringExtra("date");
        time=i.getStringExtra("time");
        dep_name=i.getStringExtra("dep_name");
        appointment_date.setText(date);
        appointment_time.setText(time);

        switch_conditions();

        setting_toolbar();

        recyclerView_doc=(RecyclerView) findViewById(R.id.recyler_view_doc);
        recyclerView_doc.setHasFixedSize(true);
        recyclerView_doc.setLayoutManager(new LinearLayoutManager(this));

       /* proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               *//* Intent i=new Intent(DoctorsList.this,PatientDetails.class);
                i.putExtra("time",time);
                i.putExtra("date",date);
                i.putExtra("id",intent_id);
                startActivity(i);*//*
            }
        });*/

    }

    private void setting_toolbar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar_doc_list);
        switch (intent_id)
        {
            case 0:
                toolbar.setTitle("Cardiology");
                toolbar.setTitleTextColor(Color.WHITE);
                dep_name=toolbar.getTitle().toString();
                break;
            case 1:
                toolbar.setTitle("Gastrology");
                toolbar.setTitleTextColor(Color.WHITE);
                dep_name=toolbar.getTitle().toString();
                break;
            case 2:
                toolbar.setTitle("Neurology");
                toolbar.setTitleTextColor(Color.WHITE);
                dep_name=toolbar.getTitle().toString();
                break;
            case 3:
                toolbar.setTitle("Neurosurgery");
                toolbar.setTitleTextColor(Color.WHITE);
                dep_name=toolbar.getTitle().toString();
                break;
            case 4:
                toolbar.setTitle("Radiation Oncology");
                toolbar.setTitleTextColor(Color.WHITE);
                dep_name=toolbar.getTitle().toString();
                break;
            case 5:
                toolbar.setTitle("Urology");
                toolbar.setTitleTextColor(Color.WHITE);
                dep_name=toolbar.getTitle().toString();
                break;
            case 6:
                toolbar.setTitle("Dentist");
                toolbar.setTitleTextColor(Color.WHITE);
                dep_name=toolbar.getTitle().toString();
                break;}
    }

    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerAdapter<ModelClass,DocViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<ModelClass, DocViewHolder>(

                ModelClass.class,
                R.layout.doc_card_view,
                DocViewHolder.class,
                databaseReference
        ) {
            @Override
            protected void populateViewHolder(final DocViewHolder viewHolder, ModelClass model, int position) {

                viewHolder.setDoc_name(model.getDoc_name());
                viewHolder.setDoc_id(model.getDoc_id());
                viewHolder.setDoc_fee(model.getDoc_fee());
                final String doctor_status= viewHolder.setDoc_status(model.getStatus());
                viewHolder.doctor_fee.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(DoctorsList.this,"Doc Status ="+doctor_status,Toast.LENGTH_LONG).show();
                         /*getDocName= (String) viewHolder.doctor_name.getText();
                        appointment_doc_name.setText(getDocName);*/
                      doc_id_ =viewHolder.doctor_id.getText().toString();
                        doc_fee=viewHolder.doctor_fee.getText().toString();

                        Intent i=new Intent(DoctorsList.this,PatientDetails.class);
                        i.putExtra("time",time);
                        i.putExtra("date",date);
                        i.putExtra("id",intent_id);
                        i.putExtra("dep_name",dep_name);
                        i.putExtra("doc_id",doc_id_);
                        i.putExtra("doc_fee",doc_fee);
                        i.putExtra("doc_status",doctor_status);
                      //  i.putExtra("patient_id",user);
                        startActivity(i);

                    }
                });
            }
        };
        recyclerView_doc.setAdapter(firebaseRecyclerAdapter);
    }

    public static class DocViewHolder extends  RecyclerView.ViewHolder
    {

      //  public TextView buttonViewOption;
      TextView doctor_name,doctor_id;
        Button doctor_fee;
        String doc_status;
        View mView;
        public DocViewHolder(View itemView) {
            super(itemView);

         //   buttonViewOption = (TextView) itemView.findViewById(R.id.textViewOptionsDoc);
           // doctor_name=(TextView) mView.findViewById(R.id.doc_name);
            mView=itemView;
        }

        public void setDoc_name(String doc_name)
        {
            doctor_name=(TextView) mView.findViewById(R.id.orignal_doc_name);
            doctor_name.setText(doc_name);
        }

        public void setDoc_id(String doc_id)
        {
            doctor_id=(TextView) mView.findViewById(R.id.orignal_doc_id);
            doctor_id.setText(doc_id);
        }

        public void setDoc_fee(String doc_fee)
        {
            doctor_fee=(Button) mView.findViewById(R.id.pay_doc);
            doctor_fee.setText(doc_fee);
        }
        public String setDoc_status(String status)
        {
            doc_status=status;
            return doc_status;
        }


    }


    public void switch_conditions()
    {
        switch (intent_id)
        {

            case 0:
                databaseReference= FirebaseDatabase.getInstance().getReference().child("Doctor_Attendance").child(dep_name).child(date).child(time);
                databaseReference.keepSynced(true);
                break;
            case 1:
                //databaseReference= FirebaseDatabase.getInstance().getReference().child("Departments").child("Gastrosurgery");
                databaseReference= FirebaseDatabase.getInstance().getReference().child("Doctor_Attendance").child(dep_name).child(date).child(time);
                databaseReference.keepSynced(true);
                break;
            case 2:
                databaseReference= FirebaseDatabase.getInstance().getReference().child("Doctor_Attendance").child(dep_name).child(date).child(time);
               // databaseReference= FirebaseDatabase.getInstance().getReference().child("Departments").child("Neurology");
                databaseReference.keepSynced(true);
                break;
            case 3:
               // databaseReference= FirebaseDatabase.getInstance().getReference().child("Departments").child("Neurosurgery");
                databaseReference= FirebaseDatabase.getInstance().getReference().child("Doctor_Attendance").child(dep_name).child(date).child(time);
                databaseReference.keepSynced(true);
                break;
            case 4:
              //  databaseReference= FirebaseDatabase.getInstance().getReference().child("Departments").child("Radiation Oncology");
                databaseReference= FirebaseDatabase.getInstance().getReference().child("Doctor_Attendance").child(dep_name).child(date).child(time);
                databaseReference.keepSynced(true);
                break;
            case 5:
               // databaseReference= FirebaseDatabase.getInstance().getReference().child("Departments").child("Urology");
                databaseReference= FirebaseDatabase.getInstance().getReference().child("Doctor_Attendance").child(dep_name).child(date).child(time);
                databaseReference.keepSynced(true);
                break;


        }
    }



}
