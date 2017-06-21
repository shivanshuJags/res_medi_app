package com.shivanshu.sak.mediappointment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText email_login,pass_login;
    TextInputLayout text_input_pass;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth=FirebaseAuth.getInstance();

        progressDialog=new ProgressDialog(this);

        mAuthListner= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() !=null)
                {
                    //startActivity(new Intent(LoginActivity.this,DoctorOrPatient.class));
                    Intent i=new Intent(LoginActivity.this,DoctorOrPatient.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                }
            }
        };

        email_login=(EditText)findViewById(R.id.email_loginField);
        pass_login=(EditText)findViewById(R.id.pass_loginField);
        text_input_pass=(TextInputLayout)findViewById(R.id.textInputLayout_pass);
        text_input_pass.setPasswordVisibilityToggleEnabled(true);
    }

    public void LoginUser(View v)
    {

        String email = email_login.getText().toString().trim();
        String pass = pass_login.getText().toString().trim();



        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)) {
            progressDialog.setMessage("Registering.....Please Wait");
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();
                    if(task.isSuccessful())
                    {

                        Intent i=new Intent(getApplicationContext(),DoctorOrPatient.class);
                        startActivity(i);
                        finish();

                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, "Errorr...", Toast.LENGTH_LONG).show();
                    }

                }
            });

        }else
        {
            Toast.makeText(LoginActivity.this, "Email OR Password is  Empty...", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListner);

    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListner != null) {
            mAuth.removeAuthStateListener(mAuthListner);
        }

    }
}


