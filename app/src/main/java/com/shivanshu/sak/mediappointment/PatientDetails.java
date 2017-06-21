package com.shivanshu.sak.mediappointment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import org.json.JSONException;
import java.math.BigDecimal;
import java.util.Calendar;


public class PatientDetails extends AppCompatActivity{

    private static final int GALLERY_REQUEST = 123;
    Spinner spinner;
    Button btnPay;
    ImageButton upload;
    ImageView upload_image;
    EditText p_name,p_phoneNo,p_dob;
    ArrayAdapter<CharSequence> arrayAdapter;
    int index_no, intent_id=0;
    String gender,time,date,dep_name,user,user_uid,dep_name_,doc_id,date_DOB,doc_status;
    private Uri imageurl = null;
    FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabasePatient;
    private StorageReference mStorage;
    public static final int PAYPAL_REQUEST_CODE = 123;
    private String paymentAmount;
    TextView doctor_id;
    int year_x,month_x,day_x,age ;
    static final int DIALOUGE_ID=0;
     Calendar current;


    private static PayPalConfiguration config = new PayPalConfiguration()
            // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(PayPalConfig.PAYPAL_CLIENT_ID);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);


        firebaseAuth=FirebaseAuth.getInstance();
        final String user_id = firebaseAuth.getCurrentUser().getEmail();
        user= user_id.split("@",2)[0];

        user_uid= firebaseAuth.getCurrentUser().getUid();


        mStorage = FirebaseStorage.getInstance().getReference();
      //  mDatabasePatient= FirebaseDatabase.getInstance().getReference().child("Patient Details").child(user).child(user_uid);
        mDatabasePatient=FirebaseDatabase.getInstance().getReference().child("Patient_Appointment");


        spinner=(Spinner) findViewById(R.id.spinner);
        p_name=(EditText)findViewById(R.id.p_name);
        p_phoneNo=(EditText)findViewById(R.id.p_phone_no);
        p_dob=(EditText)findViewById(R.id.p_dob);
        btnPay=(Button)findViewById(R.id.btn_pay) ;
        doctor_id=(TextView)findViewById(R.id.p_id) ;
        upload=(ImageButton)findViewById(R.id.upload_btn);
        upload_image=(ImageView) findViewById(R.id.document_image);


        Intent i=getIntent();
        intent_id=i.getIntExtra("id",0);
        date=i.getStringExtra("date");
        time=i.getStringExtra("time");
        dep_name_=i.getStringExtra("dep_name");
        doc_id=i.getStringExtra("doc_id");
        paymentAmount=i.getStringExtra("doc_fee");
        doc_status=i.getStringExtra("doc_status");
       // Toast.makeText(PatientDetails.this, "Hello user="+doc_id+" Dep= "+dep_name_, Toast.LENGTH_LONG).show();
        doctor_id.setText(doc_id);


        settingDOB_click();
        setting_spinner();


        Intent intent = new Intent(this, PayPalService.class);

        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        startService(intent);


        dep_id_checking();
        registeringPatient();
    }



    public void registeringPatient()
    {
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                getPayment();
            }
        });

    }

    private void getPayment() {

        //Creating a paypalpayment
        PayPalPayment payment = new PayPalPayment(new BigDecimal(paymentAmount), "USD", "MediAppointment Fee",
                PayPalPayment.PAYMENT_INTENT_SALE);

        //Creating Paypal Payment activity intent
        Intent intent = new Intent(this, PaymentActivity.class);

        //putting the paypal configuration to the intent
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        //Puting paypal payment to the intent
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        //Starting the intent activity for result
        //the request code will be used on the method onActivityResult
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //If the result is from paypal
        if (requestCode == PAYPAL_REQUEST_CODE) {

            //If the result is OK i.e. user has not canceled the payment
            if (resultCode == Activity.RESULT_OK) {
                //Getting the payment confirmation
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                inserting_data();
                //if confirmation is not null
                if (confirm != null) {

                    try {

                        //Getting the payment details
                        String paymentDetails = confirm.toJSONObject().toString(4);
                        Log.i("paymentExample", paymentDetails);

                        inserting_data();

                        //Starting a new activity for the payment details and also putting the payment details with intent
                        startActivity(new Intent(this, ConfirmationActivity.class)
                                .putExtra("PaymentDetails", paymentDetails)
                                .putExtra("dep_name",dep_name_)
                                .putExtra("doc_id",doc_id)
                                .putExtra("user_id",user)
                                .putExtra("date",date)
                                .putExtra("time",time)
                                .putExtra("patient_age",age)
                                .putExtra("doc_status",doc_status)
                                .putExtra("PaymentAmount", paymentAmount));

                    } catch (JSONException e) {
                        Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("paymentExample", "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }

        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK ) {
            imageurl = data.getData();
            upload_image.setImageURI(imageurl);

        }
    }

    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }


  public void dep_id_checking()
  {
      switch(intent_id)
      {
          case 0:
              dep_name="Cardiology";
              break;
          case 1:
              dep_name="Gastrosurgry";
              break;
          case 2:
              dep_name="Neurology";
              break;
          case 3:
              dep_name="Neurosurgery";
              break;
          case 4:
              dep_name="Radiation Oncology";
              break;
          case 5:
              dep_name="Urology";
              break;
          case 6:
              dep_name="Dentist";
              break;
      }
  }


  public void inserting_data()
  {
     final String P_name = p_name.getText().toString().trim();
    final   String P_contact = p_phoneNo.getText().toString().trim();
      final String P_dob = p_dob.getText().toString().trim();
      final String Doc_id=doctor_id.getText().toString();




      if(index_no==0)
      {
          gender="Male";
      }else if(index_no==1){
          gender="Female";
      }

      if (!TextUtils.isEmpty(P_name) && !TextUtils.isEmpty(P_contact) && !TextUtils.isEmpty(P_dob))
      {
          if(imageurl!=null) {


              StorageReference filepath = mStorage.child("Documents_Image").child(imageurl.getLastPathSegment());
              filepath.putFile(imageurl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                  @Override
                  public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                      @SuppressWarnings("VisibleForTests")
                      Uri downloadUrl = taskSnapshot.getDownloadUrl();

                      DatabaseReference newPost = mDatabasePatient.child(dep_name);
                      newPost.child("name").setValue(P_name);
                      newPost.child("contact").setValue(P_contact);
                      newPost.child("dob").setValue(P_dob);
                      newPost.child("gender").setValue(gender);
                      newPost.child("dep_id").setValue(intent_id);
                      newPost.child("status").setValue(doc_status);
                      newPost.child("Document_Image").setValue(downloadUrl.toString());

                  }
              });
          }else {
              DatabaseReference newPost=mDatabasePatient.child(dep_name).child(doc_id).child(user);
              newPost.child("name").setValue(P_name);
              newPost.child("contact").setValue(P_contact);
              newPost.child("dob").setValue(P_dob);
              newPost.child("gender").setValue(gender);
              newPost.child("dep_id").setValue(intent_id);
              newPost.child("status").setValue(doc_status);
              newPost.child("doc_id").setValue(Doc_id);
              newPost.child("patient_id").setValue(user);
              newPost.child("appointment_date").setValue(date);
              newPost.child("appointment_time").setValue(time);
          }

      } else {


      }
  }

  public void setting_spinner()
  {
      //-------------------Setting Spinner---------------

      arrayAdapter=ArrayAdapter.createFromResource(this,R.array.gender_name,android.R.layout.simple_spinner_item);
      arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      spinner.setAdapter(arrayAdapter);
      spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
              //Toast.makeText(PatientDetails.this, (int) adapterView.getItemIdAtPosition(i), Toast.LENGTH_LONG).show();
              index_no =adapterView.getSelectedItemPosition();
              //   Toast.makeText(PatientDetails.this, "Item Id="+index_no, Toast.LENGTH_LONG).show();
          }

          @Override
          public void onNothingSelected(AdapterView<?> adapterView) {

          }
      });

      //---------------------Settiing Spinner-----------
  }

  public void open_gallery(View v)
  {
          Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
          galleryIntent.setType("image/*");
          startActivityForResult(galleryIntent, GALLERY_REQUEST);

  }


  public void settingDOB_click()
  {
      p_dob.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              showDialog(DIALOUGE_ID);
          }
      });
  }

    @Override
    protected Dialog onCreateDialog(int id) {
        if(id==DIALOUGE_ID)
        {
            return new DatePickerDialog(this,dpickerListner,year_x,month_x,day_x);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener dpickerListner=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            year_x=year;
            month_x=monthOfYear+1;
            day_x=dayOfMonth;
            date_DOB=day_x+"_"+month_x+"_"+year_x;
            Toast.makeText(PatientDetails.this,"DOB= "+date_DOB, Toast.LENGTH_LONG).show();
            p_dob.setText(date_DOB);
            current=Calendar.getInstance();
            age=current.get(Calendar.YEAR)-year_x;
          //  Toast.makeText(PatientDetails.this,"age= "+age, Toast.LENGTH_LONG).show();

        }
    };
}
