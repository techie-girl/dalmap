package com.example.scott.dalmapproject;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannel();
    }

    //Set the Notification Channel Id
    String CHANNEL_ID = "dalMapNotificationChannel";

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

    @Override
    protected void onStart(){
        super.onStart();

        String[] csci1000 = getResources().getStringArray(R.array.CSCI1000);
        String[] csci1001 = getResources().getStringArray(R.array.CSCI1001);
        String[] csci1002 = getResources().getStringArray(R.array.CSCI1002);
        String[] csci1003 = getResources().getStringArray(R.array.CSCI1003);

        Date date = Calendar.getInstance().getTime();
        TextView textView = findViewById(R.id.this_text);

        String currentTime = date.toString();

        String currentHour = currentTime.substring(11,13);
        String currentMinute = currentTime.substring(14,16);
        String currentSecond = currentTime.substring(17,19);

        //TODO make this happen for the soonest class after current time, not just the first class in the users list
        //TODO For now, to test, just set the time in classes.xml csci1000[1] to be a minute or two ahead.
        int goalHour = Integer.valueOf(csci1000[1].substring(0,2));
        int goalMinute = Integer.valueOf(csci1000[1].substring(3, 5));

        //TODO correct this so that it sends when i want it, not potentially 01:02:00 later in extreme but possible case
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 0);
        c.add(Calendar.HOUR_OF_DAY, goalHour-Integer.valueOf(currentHour));
        c.add(Calendar.MINUTE, goalMinute-Integer.valueOf(currentMinute));
        c.add(Calendar.SECOND, 60-Integer.valueOf(currentSecond));
        c.add(Calendar.MILLISECOND, 0);

        textView.setText(currentHour+currentMinute+currentSecond+":"+goalHour+goalMinute+":"+c.getTimeInMillis()%System.currentTimeMillis());

        scheduleNotification(getNotification(String.valueOf(c.getTimeInMillis()%System.currentTimeMillis())), c.getTimeInMillis()%System.currentTimeMillis());
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
