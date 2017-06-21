package com.shivanshu.sak.mediappointment;

import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by shivanshu on 2/20/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<DoctorAttendence> listItems;
    private Context mCtx;
    private int rid=0,pos=0;
   public static String present,time;


    public MyAdapter(List<DoctorAttendence> listItems, Context mCtx) {
        this.listItems = listItems;
        this.mCtx = mCtx;
    }


    private String[] details = {"9 AM to 11 AM",
            "11 AM to 1 PM", "2 PM to 4 PM"};

    public String returnTime()
    {
        return time;// assuming _longitude is double
    }

    public String returnStatus()
    {
        return present;// assuming _longitude is double
    }
   /* public int returnIndex()
    {
        return pos;
    }*/





    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_attendance,parent,false);
        return new ViewHolder(v);


    }




    @Override
    public void onBindViewHolder(final ViewHolder holder, int i) {
       // LauncherActivity.ListItem listItem=listItems.get(position);

       final int postion = holder.getAdapterPosition();


        holder.time_att.setText(details[i]);
        holder.aSwitch.setChecked(false);
        holder.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (bChecked) {

                    /*DoctorAttendence doctorAttendence=new DoctorAttendence();
                  String date=  doctorAttendence.returnStatus();
                    doctorAttendence.database_setup();*/
                   present="present";
                    time = holder.time_att.getText().toString();
                   // pos=postion;
                    Toast.makeText(mCtx,"Switch is ON=",Toast.LENGTH_LONG).show();
                } else {

                  //  Toast.makeText(mCtx,"Switch is OFF",Toast.LENGTH_LONG).show();
                }
            }
        });


        if(holder.aSwitch.isChecked()){

           // Toast.makeText(mCtx,"Switch is ON",Toast.LENGTH_LONG).show();
        }
        else {
           // Toast.makeText(mCtx,"Switch is OFF",Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public int getItemCount() {
        return details.length;
    }

   public  class ViewHolder extends RecyclerView.ViewHolder
    {


         TextView time_att;
         Switch aSwitch;
       /* public ImageView itemImage;
        public TextView buttonViewOption;*/

         ViewHolder(View itemView) {
            super(itemView);

          //  itemImage = (ImageView)itemView.findViewById(R.id.item_image);
           /* time_att=(TextView) itemView.findViewById(R.id.time_att);
            aSwitch = (Switch) itemView.findViewById(R.id.switch_att);*/

        }
    }

}