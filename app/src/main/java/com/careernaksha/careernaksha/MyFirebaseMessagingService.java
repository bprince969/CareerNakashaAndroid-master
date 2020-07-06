package com.careernaksha.careernaksha;


import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
  NotificationManager notificationManager;

  public void onMessageReceived(RemoteMessage remoteMessage) {

    notificationManager =
        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

    //Setting up Notification channels for android O and above
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
      setupChannels();
    }
    int notificationId = new Random().nextInt(60000);

    Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "YOUR_CHANNEL_ID")
        .setSmallIcon(R.drawable.ic_launcher)  //a resource for your custom small icon
        .setContentTitle(remoteMessage.getData().get("title")) //the "title" value you sent in your notification
        .setContentText(remoteMessage.getData().get("message")) //ditto
        .setAutoCancel(true)  //dismisses the notification on click
        .setSound(defaultSoundUri);

    NotificationManager notificationManager =
        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

    notificationManager.notify(notificationId /* ID of notification */, notificationBuilder.build());

  }

  @SuppressLint("WrongConstant")
  @RequiresApi(api = Build.VERSION_CODES.O)
  private void setupChannels() {
    CharSequence adminChannelName = getString(R.string.notifications_admin_channel_name);
    String adminChannelDescription = getString(R.string.notifications_admin_channel_description);

    NotificationChannel adminChannel;
    adminChannel = new NotificationChannel("YOUR_CHANNEL_ID", adminChannelName, NotificationManager.IMPORTANCE_LOW);
    adminChannel.setDescription(adminChannelDescription);
    adminChannel.enableLights(true);
    adminChannel.setLightColor(Color.RED);
    adminChannel.enableVibration(true);
    if (notificationManager != null) {
      notificationManager.createNotificationChannel(adminChannel);
    }
  }
}
