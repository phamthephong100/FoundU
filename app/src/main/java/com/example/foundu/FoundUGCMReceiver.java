package com.example.foundu;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GcmListenerService;

import org.json.JSONObject;

import java.util.Random;

/**
 * Created by duongthoai on 7/30/16.
 */

public class FoundUGCMReceiver extends GcmListenerService {
    @Override
    public void onMessageReceived(String s, Bundle bundle) {
        super.onMessageReceived(s, bundle);
        sendNotification(bundle);
    }

    private void sendNotification(Bundle data) {

        String title = "FoundU";
        String message = "test test";

        try {
//            JSONObject alertContent = new JSONObject(data.getString(""));
//            JSONObject info = new JSONObject(data.getString(Constant.KEY_ALERT_INFO));
//            JSONObject alert = alertContent.getJSONObject("alert");
//            if (alert.has("title"))
//                title = alert.getString("title");
//            if (alert.has("body"))
//                message = alert.getString("body");
        }  catch (Exception ex) {
            ex.printStackTrace();
        }

        Intent intent = new Intent(this, MapsActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                .putExtra("data", data);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 121212, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                /*.setPriority(Notification.PRIORITY_MIN)*/
                .setLargeIcon(bitmap)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(212121, notificationBuilder.build());
    }
}
