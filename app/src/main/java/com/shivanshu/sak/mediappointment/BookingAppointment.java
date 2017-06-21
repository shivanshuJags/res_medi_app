package com.shivanshu.sak.mediappointment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.Calendar;
import java.util.Locale;

public class BookingAppointment extends AppCompatActivity {


    private DatabaseReference databaseReference;
    TextView txtdate;
    Toolbar toolbar;
    private RecyclerView recyclerView_book;
    private int cid=0;
    String date_,dep_name;
    Button btn911,btn111,btn103;
    MaterialCalendarView materialCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_appointment);
        intent();

        //switch_conditions();
        materialCalendarView=(MaterialCalendarView)findViewById(R.id.calendarView);
        toolbar = (Toolbar) findViewById(R.id.booking_toolbar);
        //recyclerView_book=(RecyclerView) findViewById(R.id.recylerView_book);
       /* recyclerView_book.setHasFixedSize(true);
        recyclerView_book.setLayoutManager(new LinearLayoutManager(getApplicationContext()));*/

        toolbarTitle();
        calendar_setting();


    }

    public void intent()
    {
        Intent i=getIntent();
        cid=  i.getIntExtra("key",0);
        Toast.makeText(BookingAppointment.this,"Item-Id="+cid,Toast.LENGTH_LONG).show();
    }

public void toolbarTitle()
{
    switch (cid)
    {
        case 0:
            toolbar.setTitle("Cardiology");
            dep_name=toolbar.getTitle().toString();
            break;
        case 1:
            toolbar.setTitle("Gastrology");
            dep_name=toolbar.getTitle().toString();
            break;
        case 2:
            toolbar.setTitle("Neurology");
            dep_name=toolbar.getTitle().toString();
            break;
        case 3:
            toolbar.setTitle("Neurosurgery");
            dep_name=toolbar.getTitle().toString();
            break;
        case 4:
            toolbar.setTitle("Radiation Oncology");
            dep_name=toolbar.getTitle().toString();
            break;
        case 5:
            toolbar.setTitle("Urology");
            dep_name=toolbar.getTitle().toString();
            break;
        case 6:
            toolbar.setTitle("Dentist");
            dep_name=toolbar.getTitle().toString();
            break;
    }
}

public void calendar_setting()
{
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

            txtdate=(TextView)findViewById(R.id.txtdate) ;

            int month= date.getMonth()+1;
            txtdate.setText(date.getDay()+"_"+month+"_"+date.getYear());
            date_=txtdate.getText().toString();
            //  Toast.makeText(BookingAppointment.this,"Date="+materialCalendarView.getSelectedDate(),Toast.LENGTH_LONG).show();
            setting_time();

        }
    });

}

public void setting_time()
{
    btn911=(Button)findViewById(R.id.nine_eleven);
    btn111=(Button)findViewById(R.id.eleven_one);
    btn103=(Button)findViewById(R.id.one_three);


    btn911.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            String time= btn911.getText().toString();
            String date=txtdate.getText().toString();
            Intent i=new Intent(BookingAppointment.this,PopUpActivity.class);
            i.putExtra("time",time);
            i.putExtra("date",date);
            i.putExtra("id",cid);
            i.putExtra("dep_name",dep_name);
            startActivity(i);
        }
    });

    btn111.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            String time= btn111.getText().toString();
            String date=txtdate.getText().toString();
            Intent i=new Intent(BookingAppointment.this,PopUpActivity.class);
            i.putExtra("time",time);
            i.putExtra("date",date);
            i.putExtra("id",cid);
            i.putExtra("dep_name",dep_name);
            startActivity(i);


        }
    });

    btn103.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String time= btn103.getText().toString();
            String date=txtdate.getText().toString();
            Intent i=new Intent(BookingAppointment.this,PopUpActivity.class);
            i.putExtra("time",time);
            i.putExtra("date",date);
            i.putExtra("id",cid);
            i.putExtra("dep_name",dep_name);
            startActivity(i);

        }
    });


    }

}