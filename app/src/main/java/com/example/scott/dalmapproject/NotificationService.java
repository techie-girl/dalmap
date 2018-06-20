package com.example.scott.dalmapproject;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;

import java.util.Calendar;

public class NotificationService extends Service {

    //Set the Notification Channel Id
    String CHANNEL_ID = "dalMapNotificationChannel";

    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        super.onStartCommand(intent, flags, startId);

        String[] csci1000 = getResources().getStringArray(R.array.CSCI1000);
        String[] csci1001 = getResources().getStringArray(R.array.CSCI1001);
        String[] csci1002 = getResources().getStringArray(R.array.CSCI1002);
        String[] csci1003 = getResources().getStringArray(R.array.CSCI1003);

        //TODO make it check all classes that the student is enrolled in for the soonest class.
        int goalHour = Integer.valueOf(csci1000[1].substring(0,2));
        int goalMinute = Integer.valueOf(csci1000[1].substring(3, 5));

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 0);
        c.set(Calendar.HOUR_OF_DAY, 3);
        c.set(Calendar.MINUTE, 47);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        scheduleNotification(getNotification(String.valueOf(c.getTimeInMillis()-System.currentTimeMillis())), c.getTimeInMillis()-System.currentTimeMillis());

        return START_STICKY;
    }

    //Creates a Notification Channel
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void scheduleNotification(Notification notification, long delay) {

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    private Notification getNotification(String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Class Reminder")
            .setContentText("Class starts in " + Long.parseLong(content)/60000 + " minutes")
            .setSmallIcon(R.drawable.ic_launcher_background);
        return builder.build();
    }
}
