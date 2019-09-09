package com.example.ngangainc.utility;


import android.util.Log;

import androidx.annotation.NonNull;

import com.example.ngangainc.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

	@Override
	public void onNewToken(@NonNull String s) {
		super.onNewToken(s);
		sendRegistrationToServer(s);
	}

	//send token to database
	private void sendRegistrationToServer(String token) {
		Log.d(TAG, "sendRegistrationToServer: sending token to server: " + token);
		DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
		reference.child(getString(R.string.dbnode_users))
				.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
				.child(getString(R.string.field_messaging_token))
				.setValue(token);
	}

	@Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

	String notificationBody = "";
	String notificationTitle = "";
	String notificationData = "";
	try{
	   notificationData = remoteMessage.getData().toString();
	   notificationTitle = remoteMessage.getNotification().getTitle();
	   notificationBody = remoteMessage.getNotification().getBody();
	}catch (NullPointerException e){
	   Log.e(TAG, "onMessageReceived: NullPointerException: " + e.getMessage() );
	}
	Log.d(TAG, "onMessageReceived: data: " + notificationData);
	Log.d(TAG, "onMessageReceived: notification body: " + notificationBody);
	Log.d(TAG, "onMessageReceived: notification title: " + notificationTitle);


    }

}
