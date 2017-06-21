package com.shivanshu.sak.mediappointment;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MainClass extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    private Toolbar mToolbar;
     TextView user_email,user_name;
    private DrawerLayout mDrawerLayout;
    RecyclerView recyclerView_department;
    private String playerName;
    String userEmail;
    private int rid=0;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_class);

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Department_Info");
        databaseReference.keepSynced(true);
        mAuth=FirebaseAuth.getInstance();
        setupToolbarMenu();
        setupNavigationDrawerMenu();


        recyclerView_department = (RecyclerView) findViewById(R.id.recyler_view_department);
        recyclerView_department.setHasFixedSize(true);
        recyclerView_department.setLayoutManager(new LinearLayoutManager(this));

       // TextView user_name=(TextView) findViewById(R.id.user_name);



       // mAuth=FirebaseAuth.getInstance();
       // user_email.setText("Hello");
        /*layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        listItems=new ArrayList<>();

        adapter = new MyAdapter(listItems,this);
        recyclerView.setAdapter(adapter);*/
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<MainClassModel,Departments_ViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<MainClassModel, Departments_ViewHolder>(
                MainClassModel.class,
                R.layout.card_view_layout,
                MainClass.Departments_ViewHolder.class,
                databaseReference
        ) {
            @Override
            protected void populateViewHolder(final Departments_ViewHolder viewHolder, MainClassModel model, final int position) {

                final int postion=viewHolder.getAdapterPosition();
                viewHolder.setDep_name(model.getDep_name());
                viewHolder.setDep_image(getApplicationContext(),model.getDep_image());
                viewHolder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        final PopupMenu popupMenu=new PopupMenu(MainClass.this,viewHolder.buttonViewOption);
                        popupMenu.inflate(R.menu.option_menu);
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {

                                switch (item.getItemId())
                                {
                                    case R.id.menu_1:{
                                        Intent i=new Intent(MainClass.this,BookingAppointment.class);
                                        i.putExtra("key",postion);
                                        MainClass.this.startActivity(i);
                                        finish();
                                    }
                                    break;
                                    case R.id.menu_2: {
                                        Intent i = new Intent(MainClass.this, DepartmentTreatments.class);
                                        i.putExtra("key", postion);
                                        MainClass.this.startActivity(i);
                                    }break;
                                    case R.id.menu_3: {
                                        Intent i = new Intent(MainClass.this, DoctorsDetails.class);
                                        i.putExtra("key", postion);
                                        MainClass.this.startActivity(i);
                                    }break;
                                }
                                return false;
                            }
                        });
                        popupMenu.show();
                    }
                });
            }
        };
        recyclerView_department.setAdapter(firebaseRecyclerAdapter);

    }

    private void setupToolbarMenu() {

        mToolbar = (Toolbar) findViewById(R.id.dr_after_toolbar);
        mToolbar.setTitle("MediAppointment");
    }



    private void setupNavigationDrawerMenu() {

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView.setNavigationItemSelectedListener(this);

        //---------------------Header Operation---------------
        View header=navigationView.getHeaderView(0);
        user_name=(TextView)header.findViewById(R.id.user_name);
        user_email=(TextView)header.findViewById(R.id.user_email);
        userEmail= mAuth.getCurrentUser().getEmail();
        user_email.setText(userEmail);
        String user= userEmail.split("@",2)[0];
        DatabaseReference usernameDatabase=FirebaseDatabase.getInstance().getReference().child("Users").child(user);
        usernameDatabase.child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                playerName = dataSnapshot.getValue(String.class);
                user_name.setText(playerName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        //---------------------Header Operation---------------


        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                mToolbar,
                R.string.drawer_open,
                R.string.drawer_close);

        mDrawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

    }

    @Override // Called when Any Navigation Item is Clicked
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

//		menuItem.setCheckable(true);
//		menuItem.setChecked(true);  // This helps to know which Menu Item is Clicked

        String itemName = (String) menuItem.getTitle();

       // Toast.makeText(MainClass.this, itemName + " Clicked", Toast.LENGTH_SHORT).show();

        closeDrawer();

        switch (menuItem.getItemId()) {

            case R.id.nav_free_Clinic:
                Intent i=new Intent(MainClass.this,FreeClinic.class);
                startActivity(i);
                break;

            case R.id.log_out:
                mAuth.signOut();
                Intent a=new Intent(MainClass.this,LoginActivity.class);
                startActivity(a);
                break;

            case R.id.nav_video:
                Intent j=new Intent(MainClass.this,VideoActivity.class);
                startActivity(j);
                break;

            case R.id.nav_myAppointment:
                Intent k=new Intent(MainClass.this,MyAppointment.class);
                startActivity(k);
                break;
            case R.id.nav_my_account:
                Intent l=new Intent(MainClass.this,DoctorsRegister.class);
                startActivity(l);
                break;
        }

        return true;
    }

    // Close the Drawer
    private void closeDrawer() {
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    // Open the Drawer
    private void showDrawer() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START))
            closeDrawer();
        else
            super.onBackPressed();
        Intent i=new Intent(this,RegisterActivity.class);
        startActivity(i);
    }

    public static class Departments_ViewHolder extends  RecyclerView.ViewHolder {

        TextView dp_name;
        ImageView dp_image;
        View mView;
        public TextView buttonViewOption;

        public Departments_ViewHolder(View itemView) {
            super(itemView);

            mView=itemView;

            buttonViewOption = (TextView) itemView.findViewById(R.id.textViewOptions);
        }

        public void setDep_image(final Context mCtx, final String dep_image)
        {
            dp_image=(ImageView) mView.findViewById(R.id.item_image);
            Picasso.with(mCtx).load(dep_image).into(dp_image);

            /*Picasso.with(mCtx).load(dep_image).networkPolicy(NetworkPolicy.OFFLINE).into(dp_image, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {

                    Picasso.with(mCtx).load(dep_image).into(dp_image);
                }
            });*/
        }

        public void setDep_name(String dep_name)
        {
            dp_name=(TextView) mView.findViewById(R.id.dept_name);
            dp_name.setText(dep_name);
        }
    }
}
