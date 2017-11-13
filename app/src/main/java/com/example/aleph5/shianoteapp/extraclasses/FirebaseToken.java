package com.example.aleph5.shianoteapp.extraclasses;

import android.util.Log;

public class FirebaseToken extends FirebaseInstanceIdService {
    private static final String TAG3 = "FirebaseIDService";

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG3, "Refreshed token: " + refreshedToken);

        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {

    }
}
