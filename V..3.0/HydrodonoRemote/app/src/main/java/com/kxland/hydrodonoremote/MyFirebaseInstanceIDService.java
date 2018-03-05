package com.kxland.hydrodonoremote;

import com.google.firebase.iid.FirebaseInstanceIdService;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Created by Asus on 07/09/2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFireBaseInsIDService";

    @Override
    public void onTokenRefresh() {
        //Get Update token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //String refreshedToken = FirebaseInstanceId.getInstance().;
        FirebaseMessaging.getInstance().subscribeToTopic("hydrodono");
        Log.d(TAG, "New Token" + refreshedToken);

        //Kamu bisa menyimpan nya di third party server do anything

    }

}
