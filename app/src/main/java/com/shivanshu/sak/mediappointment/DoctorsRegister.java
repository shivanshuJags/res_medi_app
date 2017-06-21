package com.shivanshu.sak.mediappointment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class DoctorsRegister extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final int GALLERY_REQUEST = 1;
    private EditText doctor_nametxt,doctor_dobtxt,doctor_phonetxt,doctor_addresstxt,doctor_citytxt,
                           doctor_statetxt,doctor_description,doctor_fee;
    private Spinner dr_dep_spinner,dr_degree_spinner,dr_experience_spinner;
    ArrayAdapter<CharSequence> dep_arrayAdapter;
    ArrayAdapter<CharSequence> exp_arrayAdapter;
    ArrayAdapter<CharSequence> degree_arrayAdapter;
    ImageView dr_profile_pic;
    Uri imageurl = null;
    Uri resultUri=null;
    String dep_name,degree_name,exp,user,player_name,user_id,uuid;
    Button save_doctor;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabaseDoctors, mDatabaseUser;
    private StorageReference mStorage;
    private ProgressDialog mProgress;
    private DrawerLayout mDrawerLayout;
    TextView user_email,user_name_txt;
    private Toolbar dr_toolbar;
    private DrawerLayout drawerLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_register);

        mProgress = new ProgressDialog(this);

        firebaseAuth=FirebaseAuth.getInstance();
         user_id= firebaseAuth.getCurrentUser().getEmail();
        user= user_id.split("@",2)[0];
         uuid=firebaseAuth.getCurrentUser().getUid();


        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabaseDoctors= FirebaseDatabase.getInstance().getReference().child("Doctors_Details");
        mDatabaseUser= FirebaseDatabase.getInstance().getReference().child("Users").child(user);

       // user_uid= firebaseAuth.getCurrentUser().getUid().toString();


        setting_toolbar();

        setupNavigationDrawerMenu_InDoctor();

      //  dr_dep_spinner=(Spinner) findViewById(R.id.dep_name_spinner);
        dr_degree_spinner=(Spinner) findViewById(R.id.dr_degree_spinner);
        dr_experience_spinner=(Spinner) findViewById(R.id.dr_experience_spinner);
        dr_profile_pic=(ImageView)findViewById(R.id.dr_profile_pic);
        doctor_nametxt =(EditText)findViewById(R.id.dr_reg_name);
        doctor_phonetxt =(EditText)findViewById(R.id.dr_reg_phone);
        doctor_addresstxt =(EditText)findViewById(R.id.dr_reg_address);
        doctor_citytxt =(EditText)findViewById(R.id.dr_reg_city);
        doctor_statetxt =(EditText)findViewById(R.id.dr_reg_state);
        doctor_fee =(EditText)findViewById(R.id.dr_fee);
        doctor_description =(EditText)findViewById(R.id.dr_reg_discription);
        save_doctor=(Button)findViewById(R.id.save_form);

        spinner_setting();



        mProgress=new ProgressDialog(this);

        save_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Save_Form();
            }
        });
    }


    public void setting_toolbar()
    {

        Intent j=getIntent();
        dep_name= j.getStringExtra("dep_name");
        Toast.makeText(DoctorsRegister.this, "new : " +dep_name , Toast.LENGTH_LONG).show();
        dr_toolbar=(Toolbar)findViewById(R.id.doctor_toolbar);
        dr_toolbar.setTitle(dep_name);

    }
    public void spinner_setting()
    {
        /*dep_arrayAdapter=ArrayAdapter.createFromResource(this,R.array.department_name,android.R.layout.simple_spinner_item);
        dep_arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dr_dep_spinner.setAdapter(dep_arrayAdapter);
        dr_dep_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos_dep, long l) {

              dep_name=  adapterView.getItemAtPosition(pos_dep).toString();
             //   Snackbar.make(view,"item is "+dep_name,Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

        exp_arrayAdapter=ArrayAdapter.createFromResource(this,R.array.doctor_experience,android.R.layout.simple_spinner_item);
        exp_arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dr_experience_spinner.setAdapter(exp_arrayAdapter);
        dr_experience_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos_exp, long l) {

                exp=  adapterView.getItemAtPosition(pos_exp).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        degree_arrayAdapter=ArrayAdapter.createFromResource(this,R.array.doctor_degree,android.R.layout.simple_spinner_item);
        degree_arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dr_degree_spinner.setAdapter(degree_arrayAdapter);
        dr_degree_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos_degree, long l) {

                degree_name=  adapterView.getItemAtPosition(pos_degree).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    public void get_Doc_Image(View v)
    {

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            imageurl = data.getData();

            CropImage.activity(imageurl)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK)
            {
                resultUri = result.getUri();
                dr_profile_pic.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    public void Save_Form()
    {
        final String dr_name=doctor_nametxt.getText().toString().trim();
        final String dr_phone=doctor_phonetxt.getText().toString().trim();
        final String dr_address=doctor_addresstxt.getText().toString().trim();
        final String dr_city=doctor_citytxt.getText().toString().trim();
        final String dr_state=doctor_statetxt.getText().toString().trim();
        final String dr_discription=doctor_description.getText().toString().trim();
        final String dr_fee=doctor_fee.getText().toString().trim();


        if (!TextUtils.isEmpty(dr_name) && !TextUtils.isEmpty(dr_phone) && !TextUtils.isEmpty(dr_city) &&
                !TextUtils.isEmpty(dr_address)&&  !TextUtils.isEmpty(dr_state) && resultUri != null) {

            mProgress.setMessage("Registering.....Please Wait");
            mProgress.show();
            StorageReference  filepath=mStorage.child("Doctors_Image").child(resultUri.getLastPathSegment());
            filepath.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    mProgress.dismiss();
                   // Snackbar.make(drawerLayout,"You Added Sucessfuly", Snackbar.LENGTH_LONG).show();
                    @SuppressWarnings("VisibleForTests")
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();

                    DatabaseReference newPost=mDatabaseDoctors.child(dep_name).child(user);

                    newPost.child("doc_name").setValue(dr_name);
                    newPost.child("Doc_Phone").setValue(dr_phone);
                    newPost.child("Doc_Address").setValue(dr_address);
                    newPost.child("Doc_City").setValue(dr_city);
                    newPost.child("Doc_State").setValue(dr_state);
                    newPost.child("dr_experience").setValue(exp);
                    newPost.child("dr_email").setValue(user_id);
                    newPost.child("dr_degree").setValue(degree_name);
                    newPost.child("dr_dep").setValue(dep_name);
                    newPost.child("dr_description").setValue(dr_discription);
                    newPost.child("dr_fee").setValue(dr_fee);
                    newPost.child("doc_image").setValue(downloadUrl.toString());
                }
            });
        }
    }

    public void setupNavigationDrawerMenu_InDoctor()
    {

        NavigationView navigationView = (NavigationView) findViewById(R.id.dr_reg_navigation_view);
        mDrawerLayout=(DrawerLayout)findViewById(R.id.dr_register_drawer);
        navigationView.setNavigationItemSelectedListener(this);

        View header=navigationView.getHeaderView(0);
        user_email=(TextView)header.findViewById(R.id.user_email);
        user_name_txt=(TextView)header.findViewById(R.id.user_name);

        mDatabaseUser.child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                player_name = dataSnapshot.getValue(String.class);
                user_name_txt.setText(player_name);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        user_email.setText(user_id);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        closeDrawer();

        switch (item.getItemId()) {

            case R.id.dr_home:
                Intent i=new Intent(DoctorsRegister.this,AfterDoctorLogin.class);
                startActivity(i);
                break;

            case R.id.dr_logOut:
                /*mAuth.signOut();
                Intent a=new Intent(AfterDoctorLogin.this,LoginActivity.class);
                startActivity(a);*/
                break;

            case R.id.dr_setting:
                Intent j = new Intent(DoctorsRegister.this, DoctorsRegister.class);
                startActivity(j);
                break;

        }
        return false;
    }

    private void closeDrawer() {
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }


}
