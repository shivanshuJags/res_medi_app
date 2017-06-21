package com.shivanshu.sak.mediappointment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DoctorAttendence extends AppCompatActivity {

    MaterialCalendarView materialCalendarView;
    private CalendarDay date;
    TextView date_txt,txt_slot_1,txt_slot_2,txt_slot_3;
    Switch aSwitch_1,aSwitch_2,aSwitch_3;
    Toolbar toolbar;
    String dep_name,user_id,user,uuid,status,date_,doc_name,doc_fee;
    String slot_1,slot_2,slot_3;
     DatabaseReference att_databaseRef,doc_database;
     FirebaseAuth firebaseAuth;

    public DoctorAttendence() {
        date = CalendarDay.today();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_attendence);

        setting_toolbar();

        firebaseAuth=FirebaseAuth.getInstance();
        user_id= firebaseAuth.getCurrentUser().getEmail();
        user= user_id.split("@",2)[0];
        uuid=firebaseAuth.getCurrentUser().getUid();
        att_databaseRef=FirebaseDatabase.getInstance().getReference().child("Doctors_Details").child(dep_name);
        doc_database=FirebaseDatabase.getInstance().getReference().child("Doctor_Attendance").child(dep_name);

        getting_doc_name();
        getting_fee();
        date_txt=(TextView)findViewById(R.id.date_txt) ;
        materialCalendarView=(MaterialCalendarView)findViewById(R.id.calendarView_att);



        settingCalendar();

    }

    private void settingCalendar() {

        Calendar calendar = Calendar.getInstance();
        materialCalendarView.setSelectedDate(calendar.getTime());

        Calendar instance1 = Calendar.getInstance();

        Calendar instance2 = Calendar.getInstance();
        instance2.set(instance2.get(Calendar.YEAR) , instance2.get(Calendar.MONTH), instance2.get(Calendar.DAY_OF_MONTH)+5);

        materialCalendarView.state().edit()
                .setMinimumDate(instance1.getTime())
                .setMaximumDate(instance2.getTime())
                .setCalendarDisplayMode(CalendarMode.WEEKS)
                .commit();

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

                txt_slot_1=(TextView)findViewById(R.id.txt_slot_1) ;
                slot_1=txt_slot_1.getText().toString();

                txt_slot_2=(TextView)findViewById(R.id.txt_slot_2) ;
                slot_2=txt_slot_2.getText().toString();

                txt_slot_3=(TextView)findViewById(R.id.txt_slot_3) ;
                slot_3=txt_slot_3.getText().toString();

                aSwitch_1=(Switch)findViewById(R.id.switch_slot_1);
                aSwitch_2=(Switch)findViewById(R.id.switch_slot_2);
                aSwitch_3=(Switch)findViewById(R.id.switch_slot_3);

                int month= date.getMonth()+1;
                date_txt.setText(date.getDay()+"_"+month+"_"+date.getYear());
                date_ =date_txt.getText().toString();
                aSwitch_setting();
            }
        });
    }
     public void setting_toolbar()
      {
           Intent i=getIntent();
           dep_name=  i.getStringExtra("dep_name");
           toolbar = (Toolbar) findViewById(R.id.att_toolbar);
          toolbar.setTitle(dep_name);
          toolbar.setTitleTextColor(Color.WHITE);
      }

         public void aSwitch_setting()
      {
          aSwitch_1.setChecked(false);

      aSwitch_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {

            if(bChecked)
            {
                status="present";
                setting_attendance_firebase_slot_1();
            }
            else
            {

                status="Not Available";
                setting_attendance_firebase_slot_1();
            }

               }
            });

          aSwitch_2.setChecked(false);
          aSwitch_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
              @Override
              public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {

                  if(bChecked)
                  {
                      status="present";
                      setting_attendance_firebase_slot_2();
                  }
                  else
                  {

                      status="Not Available";
                      setting_attendance_firebase_slot_2();
                  }
              }
          });

          aSwitch_3.setChecked(false);
          aSwitch_3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
              @Override
              public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {

                  if(bChecked)
                  {
                      status="present";
                      setting_attendance_firebase_slot_3();
                  }
                  else
                  {

                      status="Not Available";
                      setting_attendance_firebase_slot_3();
                  }
              }
          });
        }


        public void setting_attendance_firebase_slot_1()
        {

            DatabaseReference newPost=doc_database.child(date_).child(slot_1).child(user);
            newPost.child("status").setValue(status);
            newPost.child("doc_id").setValue(user);
          //  newPost.child("slot_1").setValue(slot_1);
            newPost.child("doc_name").setValue(doc_name);
            newPost.child("doc_fee").setValue(doc_fee);

        }

    public void setting_attendance_firebase_slot_2()
    {

        DatabaseReference newPost=doc_database.child(date_).child(slot_2).child(user);
        newPost.child("status").setValue(status);
        newPost.child("doc_id").setValue(user);
        //  newPost.child("slot_1").setValue(slot_1);
        newPost.child("doc_name").setValue(doc_name);
        newPost.child("doc_fee").setValue(doc_fee);

    }

    public void setting_attendance_firebase_slot_3()
    {

        DatabaseReference newPost=doc_database.child(date_).child(slot_3).child(user);
        newPost.child("status").setValue(status);
        newPost.child("doc_id").setValue(user);
        //  newPost.child("slot_1").setValue(slot_1);
        newPost.child("doc_name").setValue(doc_name);
        newPost.child("doc_fee").setValue(doc_fee);

    }


    public void getting_doc_name()
        {
            att_databaseRef.child(user).child("doc_name").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    doc_name=dataSnapshot.getValue(String.class);
                    Toast.makeText(DoctorAttendence.this,"Doc Name= "+doc_name, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        public void getting_fee()
        {
            att_databaseRef.child(user).child("dr_fee").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    doc_fee=dataSnapshot.getValue(String.class);
                    Toast.makeText(DoctorAttendence.this,"Doc Name= "+doc_name, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
}
