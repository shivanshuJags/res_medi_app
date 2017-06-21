package com.shivanshu.sak.mediappointment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

public class ConfirmationActivity extends AppCompatActivity {


    String dep_name,doc_id,user,date,time,doc_status;
    Intent intent;
    int age=0,p_age;
    private DatabaseReference mDatabaseAppointment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        TextView textViewDaparment = (TextView) findViewById(R.id.department);
        TextView textViewDocID = (TextView) findViewById(R.id.doc_id);
        TextView textViewUserID = (TextView) findViewById(R.id.user_id);
        TextView textViewPatientAge=(TextView)findViewById(R.id.patient_age_confirm) ;
        TextView textViewDocStatus=(TextView)findViewById(R.id.doc_status) ;

        intent = getIntent();

        mDatabaseAppointment= FirebaseDatabase.getInstance().getReference().child("My_Appointment");
        mDatabaseAppointment.keepSynced(true);

        try {
            JSONObject jsonDetails = new JSONObject(intent.getStringExtra("PaymentDetails"));

            //Displaying payment details 
            showDetails(jsonDetails.getJSONObject("response"), intent.getStringExtra("PaymentAmount"));
        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        getting_patient_info();
        Toast.makeText(this,"dep_name ="+dep_name+ "doc_id "+doc_id+ " age "+p_age, Toast.LENGTH_LONG).show();

        textViewDaparment.setText(dep_name);
        textViewDocID.setText(doc_id);
        textViewUserID.setText(user);
        textViewDocStatus.setText(doc_status);
        textViewPatientAge.setText(Integer.toString(p_age));

        sendingData();
    }

    private void showDetails(JSONObject jsonDetails, String paymentAmount) throws JSONException{

        TextView textViewId = (TextView) findViewById(R.id.paymentId);
        TextView textViewStatus= (TextView) findViewById(R.id.paymentStatus);
        TextView textViewAmount = (TextView) findViewById(R.id.paymentAmount);


        //Showing the details from json object
        textViewId.setText(jsonDetails.getString("id"));
        textViewStatus.setText(jsonDetails.getString("state"));
        textViewAmount.setText(paymentAmount+" USD");

    }

    public void getting_patient_info()
    {
      dep_name=  intent.getStringExtra("dep_name");
      doc_id=  intent.getStringExtra("doc_id");
       user= intent.getStringExtra("user_id");
        date= intent.getStringExtra("date");
        time= intent.getStringExtra("time");
        p_age=intent.getIntExtra("patient_age",age);
        doc_status=intent.getStringExtra("doc_status");
        Toast.makeText(ConfirmationActivity.this,"age= "+p_age, Toast.LENGTH_LONG).show();
    }

   public void sendingData()
   {
     DatabaseReference newPost=  mDatabaseAppointment.child(user).child(dep_name);
       newPost.child("doc_id").setValue(doc_id);
       newPost.child("dep_name").setValue(dep_name);
       newPost.child("date").setValue(date);
       newPost.child("time").setValue(time);
       newPost.child("status").setValue(doc_status);
      // newPost.child("age").setValue(p_age);

   }


}
