package com.shivanshu.sak.mediappointment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class DoctorsDetails extends AppCompatActivity {


    private int cid=0;
    private RecyclerView recyclerView_doctor_details;
    private DatabaseReference databaseReference;
  //  private Button see_more;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_details);

        //--------setting recylerview----------
        recyclerView_doctor_details=(RecyclerView) findViewById(R.id.recyler_view_doctor_details);
        recyclerView_doctor_details.setHasFixedSize(true);
        recyclerView_doctor_details.setLayoutManager(new LinearLayoutManager(this));
        //---------setting recylerview---------


        Intent i=getIntent();
        cid=  i.getIntExtra("key",0);

        switch_conditions();   //setting firebase




    }


    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerAdapter<DoctorDetailsModel,Doc_Details_ViewHolder> firebaseRecyclerAdapter=new
                FirebaseRecyclerAdapter<DoctorDetailsModel, Doc_Details_ViewHolder>(
                        DoctorDetailsModel.class,
                        R.layout.card_view_doctor_details,
                        DoctorsDetails.Doc_Details_ViewHolder.class,
                        databaseReference
                ) {
            @Override
            protected void populateViewHolder(Doc_Details_ViewHolder viewHolder, DoctorDetailsModel model, int position) {

                viewHolder.setDoc_name(model.getDoc_name());
                viewHolder.setDr_degree(model.getDr_degree());
                viewHolder.setDoc_experience(model.getDr_experience());
                viewHolder.setDoc_discription(model.getDr_description());
                viewHolder.setDoctor_image(getApplicationContext(),model.getDoc_image());
               // viewHolder.setImage(getApplicationContext(),model.getImage());

                viewHolder.see_more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        /*Intent intent=new Intent(DoctorsDetails.this,AfterDoctorDetails.class);
                        startActivity(intent);*/
                    }
                });


            }
        };
        recyclerView_doctor_details.setAdapter(firebaseRecyclerAdapter);
    }


    public static class Doc_Details_ViewHolder extends  RecyclerView.ViewHolder
    {

        TextView dr_name,doc_degree,dr_experience,dr_discription;
        ImageView doc_image;
        View mView;
         Button see_more;

        public Doc_Details_ViewHolder(View itemView) {
            super(itemView);

            mView=itemView;

            see_more=(Button) itemView.findViewById(R.id.see_more);
        }

        public void setDoctor_image(Context ctx, String doctor_image)
        {
            doc_image=(ImageView) mView.findViewById(R.id.doc_image);
            Picasso.with(ctx).load(doctor_image).into(doc_image);
        }

        public void setDoc_name(String doc_name)
        {
            dr_name=(TextView) mView.findViewById(R.id.dr_name);
            dr_name.setText(doc_name);
        }
        public void setDr_degree(String dr_degree)
        {
            doc_degree=(TextView) mView.findViewById(R.id.dr_degree);
            doc_degree.setText(dr_degree);
        }
       public void setDoc_experience(String doc_experience)
       {
           dr_experience=(TextView) mView.findViewById(R.id.dr_experience);
           dr_experience.setText(doc_experience);
       }
        public void setDoc_discription(String doc_discription)
        {
            dr_discription=(TextView) mView.findViewById(R.id.dr_description);
            dr_discription.setText(doc_discription);
        }
    }

    public void switch_conditions()
    {
        switch (cid)
        {

            case 0:
                databaseReference= FirebaseDatabase.getInstance().getReference().child("Doctors_Details").child("Cardiology");
                databaseReference.keepSynced(true);
                break;
            case 1:
                databaseReference= FirebaseDatabase.getInstance().getReference().child("Doctors_Details").child("Gastrosurgery");
                databaseReference.keepSynced(true);
                break;
            case 2:
                databaseReference= FirebaseDatabase.getInstance().getReference().child("Doctors_Details").child("Neurology");
                databaseReference.keepSynced(true);
                break;
            case 3:
                databaseReference= FirebaseDatabase.getInstance().getReference().child("Doctors_Details").child("Neurosurgery");
                databaseReference.keepSynced(true);
                break;
            case 4:
                databaseReference= FirebaseDatabase.getInstance().getReference().child("Doctors_Details").child("Radiation Oncology");
                databaseReference.keepSynced(true);
                break;
            case 5:
                databaseReference= FirebaseDatabase.getInstance().getReference().child("Doctors_Details").child("Urology");
                databaseReference.keepSynced(true);
                break;
            case 6:
                databaseReference= FirebaseDatabase.getInstance().getReference().child("Doctors_Details").child("Dentist");
                databaseReference.keepSynced(true);
                break;


        }
    }

}
