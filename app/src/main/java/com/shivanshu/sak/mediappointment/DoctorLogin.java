package com.shivanshu.sak.mediappointment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DoctorLogin extends AppCompatActivity {

    private EditText dr_email_login,dr_pass_login;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login);

        mAuth=FirebaseAuth.getInstance();

        progressDialog=new ProgressDialog(this);

        mAuthListner= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser() !=null)
                {
                    startActivity(new Intent(DoctorLogin.this,AfterDoctorLogin.class));
                    // finish();

                }
            }
        };

        dr_email_login=(EditText)findViewById(R.id.dr_email_txt);
        dr_pass_login=(EditText)findViewById(R.id.dr_pass_txt);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListner);
    }

    public void LoginDoctor(final View v)
    {

        String dr_email = dr_email_login.getText().toString().trim();
        String dr_pass = dr_pass_login.getText().toString().trim();


        if(!TextUtils.isEmpty(dr_email) && !TextUtils.isEmpty(dr_pass))
        {
            progressDialog.setMessage("Signing In.....Please Wait");
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(dr_email,dr_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful())
                    {

                        Intent i=new Intent(getApplicationContext(),AfterDoctorLogin.class);
                        startActivity(i);
                        finish();

                    }
                    else
                    {
                        Snackbar.make(v,"Error in Signing In..",Snackbar.LENGTH_LONG).show();
                    }
                }
            });
        }
        else
        {
            Snackbar.make(v,"Need to Fill All Details",Snackbar.LENGTH_LONG).show();
        }

    }
}
