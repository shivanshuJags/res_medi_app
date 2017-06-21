package com.shivanshu.sak.mediappointment;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdReceiver;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by shivanshu on 4/4/2017.
 */

public class MyFirebaseInstanceIDServices extends FirebaseInstanceIdService  {

    private static final String REG_TOKEN="REG_TOKEN";

    @Override
    public void onTokenRefresh() {

        String recentToken= FirebaseInstanceId.getInstance().getToken();
        Log.d(REG_TOKEN,recentToken);
    }


}
