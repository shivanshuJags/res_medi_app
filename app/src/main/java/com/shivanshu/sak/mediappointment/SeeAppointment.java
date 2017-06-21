package com.shivanshu.sak.mediappointment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SeeAppointment extends AppCompatActivity {

    Toolbar toolbar;
    String dep_name,user,patient_id;
    private DatabaseReference appDatabase,update_post;
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView_see_appointment;
    private CoordinatorLayout coordinatorLayout;
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_appointment);

        mAuth=FirebaseAuth.getInstance();
        final String user_id= mAuth.getCurrentUser().getEmail();
        user= user_id.split("@",2)[0];

        setting_toolbar();

       appDatabase=FirebaseDatabase.getInstance().getReference().child("Patient_Appointment").child(dep_name).child(user);

        recyclerView_see_appointment = (RecyclerView) findViewById(R.id.recyclerView_see_appointment);
        recyclerView_see_appointment.setHasFixedSize(true);
        recyclerView_see_appointment.setLayoutManager(new LinearLayoutManager(this));

        coordinatorLayout=(CoordinatorLayout)findViewById(R.id.c_layout);

    }

    private void setting_toolbar() {
        toolbar=(Toolbar)findViewById(R.id.toolbar_see_appt);
        Intent i=getIntent();
        dep_name=i.getStringExtra("dep_name");
       toolbar.setTitle(dep_name);
        toolbar.setTitleTextColor(Color.WHITE);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<SeeAppointmentModel,SeeAppoinmentViewHolder> firebaseRecylerAdapter=new FirebaseRecyclerAdapter<SeeAppointmentModel, SeeAppoinmentViewHolder>(
                SeeAppointmentModel.class,
                R.layout.card_view_see_appointment,
                SeeAppoinmentViewHolder.class,
                appDatabase
        ) {
            @Override
            protected void populateViewHolder(SeeAppoinmentViewHolder viewHolder, SeeAppointmentModel model, int position) {


                viewHolder.setPatient_name(model.getName());
                viewHolder.setPatient_id(model.getPatient_id());
                patient_id=viewHolder.txt_P_id.getText().toString();
                count= recyclerView_see_appointment.getChildCount()+1;
                Snackbar.make(coordinatorLayout,"You "+count, Snackbar.LENGTH_LONG).show();

            }
        };
        recyclerView_see_appointment.setAdapter(firebaseRecylerAdapter);

    }

    private static class SeeAppoinmentViewHolder extends RecyclerView.ViewHolder
    {
        TextView txt_P_name,txt_P_id;
        View mView;

        public SeeAppoinmentViewHolder(View itemView)
        {
            super(itemView);
            mView=itemView;
        }
        public void setPatient_name(String patient_name)
        {
            txt_P_name=(TextView) mView.findViewById(R.id.see_p_name);
            txt_P_name.setText(patient_name);
        }
        public void setPatient_id(String patient_id)
        {
            txt_P_id=(TextView) mView.findViewById(R.id.see_p_id);
            txt_P_id.setText(patient_id);

        }
    }

    public void canceling_appointment(View v)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(SeeAppointment.this);
        builder.setMessage("Sure you want to cancel this Appointment !")
                .setCancelable(false)
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String MESSAGE="Your Appointment has been Cancelled";
                        update_post=FirebaseDatabase.getInstance().getReference().child("My_Appointment").child(patient_id);
                        DatabaseReference updateStatus=update_post.child(dep_name);
                        updateStatus.child("status").setValue(MESSAGE);

                        DatabaseReference deleteAppointment=appDatabase;
                        deleteAppointment.child(patient_id).removeValue();
                        finish();
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
}
