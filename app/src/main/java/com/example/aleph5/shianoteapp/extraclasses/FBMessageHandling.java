package com.example.aleph5.shianoteapp.extraclasses;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FBMessageHandling extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
// FOR FOREGROUND APP
        if(remoteMessage.getData().size()>0){
            String shortInfo = remoteMessage.getData().get("shortInfo");
            //String message =remoteMessage.getData().get("description");

            Intent intent = new Intent("com.example.aleph5.shianoteapp_FBMH");
            intent.putExtra("shortInfo", shortInfo);
            //intent.putExtra("description", message);
            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
            localBroadcastManager.sendBroadcast(intent);
        }
    }
}