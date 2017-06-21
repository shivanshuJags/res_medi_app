package com.shivanshu.sak.mediappointment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyAppointment extends AppCompatActivity {

    Toolbar toolbar;
    DatabaseReference databaseReference, database_my_appointment, databaseDocDetails;
    FirebaseAuth mAuth;
    String user_id, user, user_uid, department_name, doc_id;
    Button schedule_appointment;
    RecyclerView my_appointment_rv;
    FirebaseRecyclerAdapter<MyAppointmentModel, MyAppointment_ViewHolder> firebaseRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appointment);

        toolbar = (Toolbar) findViewById(R.id.booking_toolbar);
        setSupportActionBar(toolbar);

        schedule_appointment=(Button)findViewById(R.id.schedule_appoint);
        schedule_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MyAppointment.this,MainClass.class));
            }
        });

        mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getEmail();
        user = user_id.split("@", 2)[0];
        user_uid = mAuth.getCurrentUser().getUid();

        my_appointment_rv = (RecyclerView) findViewById(R.id.my_appointment_rv);
        my_appointment_rv.hasFixedSize();
        my_appointment_rv.setLayoutManager(new LinearLayoutManager(this));

        databaseReference = FirebaseDatabase.getInstance().getReference().child("My_Appointment");
        databaseReference.keepSynced(true);

        database_my_appointment = FirebaseDatabase.getInstance().getReference().child("My_Appointment").child(user);
        database_my_appointment.keepSynced(true);

        checking_exist();
    }

    public void checking_exist() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(user).exists()) {
                    my_appointment_rv.setAdapter(firebaseRecyclerAdapter);

                } else {
                    alertBox();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void alertBox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MyAppointment.this);
        builder.setMessage("Appointment not Booked Yet !")
                .setCancelable(false)
                .setPositiveButton("Book", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent j = new Intent(MyAppointment.this, MainClass.class);
                        startActivity(j);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                           dialogInterface.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.setTitle("Alert !!!");
        alert.show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<MyAppointmentModel, MyAppointment_ViewHolder>(
                MyAppointmentModel.class,
                R.layout.card_layout_my_appointment,
                MyAppointment.MyAppointment_ViewHolder.class,
                database_my_appointment
        ) {
            @Override
            protected void populateViewHolder(final MyAppointment_ViewHolder viewHolder, final MyAppointmentModel model, int position) {

                viewHolder.setDep_name(model.getDep_name());
                viewHolder.setDoc_id(model.getDoc_id());
                viewHolder.setTime(model.getTime());
                viewHolder.setDate(model.getDate());
                viewHolder.setStatus(model.getStatus());
                department_name = model.getDep_name();
                doc_id = model.getDoc_id();

                viewHolder.imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        database_my_appointment.child(department_name).removeValue();
                    }
                });

                viewHolder.callbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       // doc_id = model.getDoc_id();
                        phone();
                    }
                });

                viewHolder.mailbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                       // mail();
                        getting_email();
                    }
                });
            }
        };
    }

    public static class MyAppointment_ViewHolder extends RecyclerView.ViewHolder {

        TextView dep_name_txt, time_txt, date_txt, doc_id_txt, status_txt;
        ImageButton imageButton;
        View mView;
        Button callbtn,mailbtn;


        public MyAppointment_ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
            imageButton = (ImageButton) mView.findViewById(R.id.close);
            callbtn = (Button) mView.findViewById(R.id.call_appointment);
            mailbtn=(Button)mView.findViewById(R.id.mail_btn);
        }

        public void setDep_name(String dep_name) {
            dep_name_txt = (TextView) mView.findViewById(R.id.my_dep_txt);
            dep_name_txt.setText(dep_name);
        }

        public void setDate(String date) {
            date_txt = (TextView) mView.findViewById(R.id.my_date_txt);
            date_txt.setText(date);
        }

        public void setTime(String time) {
            time_txt = (TextView) mView.findViewById(R.id.my_time_txt);
            time_txt.setText(time);
        }

        public void setDoc_id(String doc_id) {
            doc_id_txt = (TextView) mView.findViewById(R.id.my_docID_txt);
            doc_id_txt.setText(doc_id);
        }

        public void setStatus(String status) {
            status_txt = (TextView) mView.findViewById(R.id.my_status_txt);
            status_txt.setText(status);
        }
    }

    public void phone() {
        databaseDocDetails = FirebaseDatabase.getInstance().getReference().child("Doctors_Details").child(department_name).child(doc_id).child("Doc_Phone");
        databaseDocDetails.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String phone_doc = dataSnapshot.getValue(String.class);
                // Toast.makeText(MyAppointment.this,"phone_doc= "+phone_doc, Toast.LENGTH_LONG).show();
                Intent call_manager = new Intent(Intent.ACTION_CALL);
                call_manager.setData(Uri.parse("tel:" +phone_doc));
                if (ActivityCompat.checkSelfPermission(MyAppointment.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(call_manager);
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });
    }

    public void getting_email()
    {
        DatabaseReference email_ref=FirebaseDatabase.getInstance().getReference().child("Doctors_Details").child(department_name).child(doc_id).child("dr_email");
        email_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String email = dataSnapshot.getValue(String.class);
               // Toast.makeText(MyAppointment.this,"email = "+email, Toast.LENGTH_LONG).show();
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_EMAIL,new String[]{ email});
                emailIntent.setType("message/rfc822");
                startActivity(emailIntent);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}