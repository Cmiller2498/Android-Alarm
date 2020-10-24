package com.example.timer20;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

class timeBroadcastReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(2000);
        Notification noti = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            noti = new Notification.Builder(context).setContentTitle("Alarm Firing").setContentText("One-Time Alarm").setSmallIcon(R.mipmap.ic_launcher).build();
        }


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifyme")
               .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Alarm: " + timerActivity.alarmMessage)
                .setContentText("Longitude: " + " Latitude: " );


        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

        notificationManagerCompat.notify(200, builder.build());


        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        noti.flags = Notification.FLAG_AUTO_CANCEL;
        manager.notify(0, noti);

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        Ringtone r = RingtoneManager.getRingtone(context, notification);

        try {
            r.play();
            Thread.sleep(10000);
            r.stop();
        }
        catch(InterruptedException e){
            System.out.println("interrupted");
        }

    }
}
