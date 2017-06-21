package com.shivanshu.sak.mediappointment;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class VideoActivity extends AppCompatActivity {

    CardView snake,burn,antibiotic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        setting_toolbar_video();
          /*  txtsnake=(TextView)findViewById(R.id.txtsnake);
            txtsnake.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i=new Intent(VideoActivity.this,YoutubeActivity.class);
                    startActivity(i);
                }
            });*/
        setting_id();
        }

        public void setting_toolbar_video()
        {
            Toolbar toolbarV=(Toolbar)findViewById(R.id.video_toolbar);
            toolbarV.setTitle("First Aid Video");
            toolbarV.setTitleTextColor(Color.WHITE);
            setSupportActionBar(toolbarV);
            ImageButton imgHome=(ImageButton)findViewById(R.id.img_home);
            imgHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    startActivity(new Intent(VideoActivity.this,MainClass.class));
                }
            });
        }

        public void setting_id()
        {

            snake=(CardView)findViewById(R.id.cv_snake);
            snake.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(VideoActivity.this,YoutubeActivity.class).putExtra("cv_id",0));

                }
            });
            burn=(CardView)findViewById(R.id.cv_burn);
            burn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(VideoActivity.this,YoutubeActivity.class).putExtra("cv_id",1));
                }
            });
            antibiotic=(CardView)findViewById(R.id.cv_antibiotic);
            antibiotic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(VideoActivity.this,YoutubeActivity.class).putExtra("cv_id",2));
                }
            });
        }
}
