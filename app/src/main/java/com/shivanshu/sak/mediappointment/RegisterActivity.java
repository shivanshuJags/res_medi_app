package com.shivanshu.sak.mediappointment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.auth.api.signin.internal.SignInConfiguration;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterActivity extends AppCompatActivity {


    private EditText nametxt, emailtxt, phonetxt, passtxt, repasstxt;
    TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutPassword, inputLayoutRePassword, inputLayoutContact;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabaseUsers;
    //  private FirebaseAuth.AuthStateListener mAuthStateListener;
    String user_gmail_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mProgress = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");
        mDatabaseUsers.keepSynced(true);

        initializeWidgets();


    }



    public void Register(View v) {
        String name = nametxt.getText().toString().trim();
        String email = emailtxt.getText().toString().trim();
        String phone = phonetxt.getText().toString().trim();
        String password = passtxt.getText().toString().trim();
        String repassword = repasstxt.getText().toString().trim();
        boolean isValid=true;

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(password) || TextUtils.isEmpty(repassword)) {

            Toast.makeText(RegisterActivity.this, "Email OR Password is  Empty...", Toast.LENGTH_LONG).show();
        }
       else {
            if (password.equals(repassword)) {
                mProgress.setMessage("Registering.....Please Wait");
                mProgress.show();


                firebaseAuth.createUserWithEmailAndPassword(email, repassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mProgress.dismiss();
                        if (!task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Invalid E-Mail Id", Toast.LENGTH_LONG).show();
                        } else {
                            UsersDetail();
                            Toast.makeText(RegisterActivity.this, "Registered Succesfully..", Toast.LENGTH_LONG).show();
                            finish();
                            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(i);
                        }
                    }
                });
            }
        }
    }

    public void UsersDetail() {
        String name = nametxt.getText().toString().trim();
        String phone = phonetxt.getText().toString().trim();
        final String user_email = firebaseAuth.getCurrentUser().getEmail();
        user_gmail_id = user_email.split("@", 2)[0];
      //  String new_user_gmail_id=user_gmail_id.replace(".","_");

        DatabaseReference newPost = mDatabaseUsers.child(user_gmail_id);
        newPost.child("name").setValue(name);
        newPost.child("phone").setValue(phone);
        newPost.child("user").setValue(user_gmail_id);

    }

    public void loginPage(View v) {
        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(i);
    }

    private void initializeWidgets() {

        inputLayoutName = (TextInputLayout) findViewById(R.id.textInputLayout_name);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayout_Email);
        inputLayoutContact = (TextInputLayout) findViewById(R.id.textInputLayout_contact);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayout_password);
        inputLayoutRePassword = (TextInputLayout) findViewById(R.id.textInputLayout_repassword);

        nametxt = (EditText) findViewById(R.id.name_Field);
        emailtxt = (EditText) findViewById(R.id.email_Field);
        phonetxt = (EditText) findViewById(R.id.phone_Field);
        passtxt = (EditText) findViewById(R.id.password_Field);
        repasstxt = (EditText) findViewById(R.id.repassword_Field);
    }

}