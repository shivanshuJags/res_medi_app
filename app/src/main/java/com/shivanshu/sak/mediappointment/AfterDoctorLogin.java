package com.shivanshu.sak.mediappointment;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class AfterDoctorLogin extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Toolbar toolbar;
    TextView user_email,user_name_txt,total;
    String dr_email,user,dep_name,currentDate;
    private String playerName;
    private DrawerLayout mDrawerLayout;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseUsers,current_database;
    Calendar current_date;
    ImageButton img_right;
    FloatingActionButton fab;
    FirebaseRecyclerAdapter<SeeAppointmentModel, AfterDocLoginViewHolder> appointmentRecyclerAdapter;
    CoordinatorLayout coordinatorLayout;
    RecyclerView app_rv;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_doctor_login);

        mAuth=FirebaseAuth.getInstance();
        final String user_id= mAuth.getCurrentUser().getEmail();
        user= user_id.split("@",2)[0];
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users").child(user);
        mDatabaseUsers.keepSynced(true);

        Intent i=getIntent();
        dep_name=  i.getStringExtra("dep_name");


        setting_toolbar();
        setupNavigationDrawerMenu_InDoctor();

        current_date_fetch();
        img_right=(ImageButton) findViewById(R.id.img_right);
        img_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(AfterDoctorLogin.this,SeeAppointment.class);
                i.putExtra("dep_name",dep_name);
                startActivity(i);
            }
        });

       //
        // total=(TextView)findViewById(R.id.total_appointment) ;

        coordinatorLayout=(CoordinatorLayout) findViewById(R.id.co_layout) ;
        fab =(FloatingActionButton)findViewById(R.id.fab_front_page);
        //fab.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.add));
        fab.setImageDrawable(ContextCompat.getDrawable(getBaseContext(),R.drawable.add));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AfterDoctorLogin.this,DoctorsRegister.class). putExtra("dep_name",dep_name));
            }
        });
        current_database = FirebaseDatabase.getInstance().getReference().child("Patient_Appointment").child(dep_name).child(user);
        current_database.keepSynced(true);

        app_rv=(RecyclerView)findViewById(R.id.rv_appointment);
        app_rv.setHasFixedSize(true);
        app_rv.setLayoutManager(new LinearLayoutManager(this));

        checking_exist();
    }

    public void setting_toolbar()
    {
        toolbar=(Toolbar)findViewById(R.id.dr_toolbar);
        toolbar.setTitle(dep_name);
       // Toast.makeText(getApplicationContext(), "new : " +dep_name , Toast.LENGTH_LONG).show();
    }

    public void setupNavigationDrawerMenu_InDoctor()
    {

        NavigationView navigationView = (NavigationView) findViewById(R.id.dr_navigation_view);
        mDrawerLayout=(DrawerLayout)findViewById(R.id.dr_drawer_layout);
        navigationView.setNavigationItemSelectedListener(this);

        View header=navigationView.getHeaderView(0);
        user_email=(TextView)header.findViewById(R.id.user_email);
        user_name_txt=(TextView)header.findViewById(R.id.user_name);

        dr_email= mAuth.getCurrentUser().getEmail();

        mDatabaseUsers.child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                playerName = dataSnapshot.getValue(String.class);
                user_name_txt.setText(playerName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        user_email.setText(dr_email);


        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close);

        mDrawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        closeDrawer();

        switch (item.getItemId()) {

            case R.id.dr_seeAppointment:
                Intent i=new Intent(AfterDoctorLogin.this,SeeAppointment.class);
                startActivity(i);
                break;

            case R.id.dr_logOut:
                mAuth.signOut();
                Intent a=new Intent(AfterDoctorLogin.this,LoginActivity.class);
                startActivity(a);
                break;

            case R.id.dr_setting:
                Intent j = new Intent(AfterDoctorLogin.this, DoctorsRegister.class);
                j.putExtra("dep_name",dep_name);
                startActivity(j);
               // finish();
                break;

            case R.id.dr_attendance:
                Intent k = new Intent(AfterDoctorLogin.this, DoctorAttendence.class);
                k.putExtra("dep_name",dep_name);
                startActivity(k);
                // finish();
                break;

        }
            return false;
    }

    private void closeDrawer() {
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }
    private void showDrawer() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START))
        {closeDrawer();}

        else{super.onBackPressed();}
    }

    @Override
    protected void onStart() {
        super.onStart();
        appointmentRecyclerAdapter=new FirebaseRecyclerAdapter<SeeAppointmentModel, AfterDocLoginViewHolder>(
                SeeAppointmentModel.class,
                R.layout.card_view_see_appointment,
                AfterDocLoginViewHolder.class,
                current_database
        ) {
            @Override
            protected void populateViewHolder(AfterDocLoginViewHolder viewHolder, SeeAppointmentModel model, int position) {

                viewHolder.setPatient_name(model.getName());
                viewHolder.setPatient_id(model.getPatient_id());
            }
        };


    }

    public void current_date_fetch()
    {
        current_date=Calendar.getInstance();
        int x_month=current_date.get(Calendar.MONTH)+1;
        int x_year=current_date.get(Calendar.YEAR);
        int x_day=current_date.get(Calendar.DAY_OF_MONTH);

        currentDate=x_day+"_"+x_month+"_"+x_year;
        //Toast.makeText(AfterDoctorLogin.this,currentDate,Toast.LENGTH_LONG).show();
    }
    public void checking_exist()
    {

        current_database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child("appointment_date").exists()) {
                        String fetched_date=data.child("appointment_date").getValue(String.class);
                       // Toast.makeText(AfterDoctorLogin.this,fetched_date,Toast.LENGTH_LONG).show();
                        if(fetched_date.equals(currentDate))
                        {

                          //  count= app_rv.getChildCount()+1;
                            app_rv.setAdapter(appointmentRecyclerAdapter);
                           // total.setText(count);
                        }
                    } else {
                        //do something
                        Toast.makeText(AfterDoctorLogin.this,"No Child",Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });
    }

    public static class AfterDocLoginViewHolder extends RecyclerView.ViewHolder{

        TextView txt_P_name,txt_P_id;
        View mView;

        public AfterDocLoginViewHolder(View itemView) {
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
}
