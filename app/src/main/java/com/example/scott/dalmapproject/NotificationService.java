package com.example.scott.dalmapproject;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

public class NotificationService extends Service {

    //Set the Notification Channel Id and Notification Id
    int notificationId = 1;
    String CHANNEL_ID = "dalMapNotificationChannel";
    NotificationCompat.Builder mBuilder;
    NotificationManagerCompat notificationManager;

    //Bind the service
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        //TODO make the notification send at a better time
        //currently sends when the app closes for any reason
        notificationManager.notify(notificationId, mBuilder.build());

        return START_STICKY;
    }

    @Override
    public void onCreate() {

        //Build the notification
        mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(getString(R.string.notification_title))
                .setContentText(getString(R.string.notification_content))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        notificationManager = NotificationManagerCompat.from(this);

    }
}