package com.shivanshu.sak.mediappointment;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YoutubeActivity extends YouTubeBaseActivity
        implements YouTubePlayer.OnInitializedListener {

    static final String GOOGLE_API_KEY="AIzaSyC6pEv-6ZFcdVbBa5GfHA8lkMeA4I6pJqk";
    static final String YOUTUBE_VIDEO_ID="IVIXd5_Kcsc";
    static final String YOUTUBE_VIDEO_ID_1="XzIxte3QJKM";
    static final String YOUTUBE_VIDEO_ID_2="RVZWXp3Wgao";
    static final String YOUTUBE_PLAYLIST="TODO";
    private static final String TAG = "YoutubeActivity";
    int cv_id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        Intent intent=getIntent();
        cv_id= intent.getIntExtra("cv_id",0);
        ConstraintLayout constraintLayout=(ConstraintLayout) getLayoutInflater().inflate(R.layout.activity_youtube, null);
        setContentView(constraintLayout);

        YouTubePlayerView player=new YouTubePlayerView(this);
        player.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        constraintLayout.addView(player);
        player.initialize(GOOGLE_API_KEY,this);
    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {

        Log.d(TAG, "onInitializationSuccess: provider is: " +provider.getClass().toString());

        if(!wasRestored)
        {
            switch (cv_id)
            {
                case 0:
                    youTubePlayer.cueVideo(YOUTUBE_VIDEO_ID);
                    break;
                case 1:
                    youTubePlayer.cueVideo(YOUTUBE_VIDEO_ID_1);
                    break;
                case 2:
                    youTubePlayer.cueVideo(YOUTUBE_VIDEO_ID_2);
                    break;
            }
          //  youTubePlayer.cueVideo(YOUTUBE_VIDEO_ID);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

        final int REQUEST_CODE = 1;
        if(youTubeInitializationResult.isUserRecoverableError())
        {
            youTubeInitializationResult.getErrorDialog(this, REQUEST_CODE).show();
        }else
        {
            String errorMessage=String.format("There was an error initializing the Youtube Player (%1$s)", youTubeInitializationResult.toString());
            Toast.makeText(YoutubeActivity.this,errorMessage, Toast.LENGTH_LONG).show();
        }
    }
}
