package com.pnf.elar.app.pushnotification;

import java.util.HashMap;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.pnf.elar.app.Drawer;
import com.pnf.elar.app.R;
import com.pnf.elar.app.UserSessionManager;
import com.pnf.elar.app.util.AppLog;

public class GCMNotificationIntentService extends IntentService {
    // Sets an ID for the notification, so it can be updated
    public static int notifyID = 1;
    NotificationCompat.Builder builder;

    UserSessionManager session;

    public GCMNotificationIntentService() {
        super("GcmIntentService");
    }

    public static final String TAG = "GCMNotificationIntentService";

    Context ctx;

    @Override
    protected void onHandleIntent(Intent intent) {

        ctx = this;

        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
                    .equals(messageType)) {
                sendNotification("Send error: " + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
                    .equals(messageType)) {
                sendNotification("Deleted messages on server: "
                        + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
                    .equals(messageType)) {

                AppLog.Log("extras", extras.toString());


                sendNotification("" + extras.get(ApplicationConstants.MSG_KEY)); // When
                // Message
                // is
                // received
                // normally
                // from
                // GCM
                // Cloud
                // Server
            }
        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    private void sendNotification(String greetMsg) {
        try {

            session = new UserSessionManager(getApplicationContext());
            HashMap<String, String> user = session.getUserDetails();
            String UserType = user.get(UserSessionManager.TAG_user_type);
            AppLog.Log("LMS", greetMsg);
//            Intent resultIntent = new Intent(this, Drawer.class);
//            resultIntent.putExtra("greetjson", greetMsg);
//            resultIntent.setAction(Intent.ACTION_MAIN);
//            resultIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            Intent notificationIntent=null;

            NotificationCompat.Builder mNotifyBuilder;
            NotificationManager mNotificationManager;
            mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


            if(greetMsg != null){
                notificationIntent = new Intent(ctx, Drawer.class);
                notificationIntent.putExtra("MsgType", greetMsg);
                notificationIntent.setAction(greetMsg);
//                notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            }

            PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0,
                    notificationIntent, 0);
            NotificationCompat.Builder notificationBuilder; /*= new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(messageText)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);*/
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                mNotifyBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.app_icon_trans)
                        .setContentTitle("SCM")
                        .setAutoCancel(true)
                        .setColor(ContextCompat.getColor(ctx,R.color.lms_grey))
                        .setVisibility(Notification.VISIBILITY_PUBLIC)
                        .setSound(defaultSoundUri)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(greetMsg))
                        .setContentIntent(resultPendingIntent)
                        .setContentText(greetMsg);
            } else {
                mNotifyBuilder = new NotificationCompat.Builder(this)
                        .setContentTitle("SCM")
                        .setSmallIcon(R.drawable.app_icon)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(resultPendingIntent)
                        .setContentText(greetMsg);
            }


            // Set pending intent
//            mNotifyBuilder.setContentIntent(resultPendingIntent);

            int defaults = 0;
            defaults = defaults | Notification.DEFAULT_LIGHTS;
            defaults = defaults | Notification.DEFAULT_VIBRATE;
            defaults = defaults | Notification.DEFAULT_SOUND;

            mNotifyBuilder.setDefaults(defaults);
            // Set the content for Notification

            // Set autocancel
            mNotifyBuilder.setAutoCancel(true);
            // Post a notification
            mNotificationManager.notify(notifyID, mNotifyBuilder.build());
            notifyID++;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
