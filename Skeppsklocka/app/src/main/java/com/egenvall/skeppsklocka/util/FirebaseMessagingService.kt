package com.egenvall.skeppsklocka.util

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.egenvall.skeppsklocka.R
import com.egenvall.skeppsklocka.ui.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class FirebaseMessagingService : FirebaseMessagingService() {


    private val TAG = "FbMessagingService"

    /**
     * Called when message is received.

     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        //TODO Play Services after app was in background
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        Log.d(TAG, "From: " + remoteMessage!!.from)



        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: " + remoteMessage.data.get("message"));
            sendNotification(remoteMessage.data.get("message"))
        }
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            var body : String? = remoteMessage.notification.body
            //sendNotification(body)
        }

    }

    /**
     * Create and show a simple notification containing the received FCM message.

     * @param messageBody FCM message body received.
     */
    private fun sendNotification(messageBody: String?) {
        val builder = NotificationCompat.Builder(this).setContentTitle("Notifications Example").setContentText(messageBody)
        val notificationIntent = Intent(this, MainActivity::class.java)
        val contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT)
        val sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                + "://" + getPackageName() + "/raw/shipbell");


        builder.setSound(sound)
        builder.setSmallIcon(R.mipmap.ic_launcher)
        builder.setContentIntent(contentIntent)
        builder.setAutoCancel(true)
        builder.setLights(Color.BLUE, 500, 500)
        val pattern = longArrayOf(500, 500, 500, 500, 500, 500, 500, 500, 500)
        builder.setVibrate(pattern)
        builder.setStyle(NotificationCompat.InboxStyle())
// Add as notification
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(1, builder.build())
    }
}