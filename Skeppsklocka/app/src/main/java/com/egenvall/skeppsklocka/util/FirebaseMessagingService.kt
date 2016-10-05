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
import org.jetbrains.anko.intentFor

/**
 * Class for handling FCM messages.
 * Our Messages only include data payload
 */
class FirebaseMessagingService : FirebaseMessagingService() {
    private val TAG = "FirebaeMessagingService"

    /**
     * Called when message is received.
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        Log.d(TAG, "From: " + remoteMessage!!.from)
        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            val message = remoteMessage?.data.get("message") ?: "Message was null, passing this instead"
            Log.d(TAG, "Message data payload: ${message}")
            sendNotification(message)
        }
    }

    /**
     * Create and show a simple notification containing the received FCM message.

     * @param messageBody FCM message body received.
     */
    private fun sendNotification(messageBody: String) {
        val builder = NotificationCompat.Builder(this).setContentTitle("Crepido").setContentText("Milstolpe!!")
        val notificationIntent = intentFor<MainActivity>("message" to messageBody)
        val contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT)
        val sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                + "://" + getPackageName() + "/raw/shipbell");

        builder.setSound(sound)
        builder.setSmallIcon(R.mipmap.ic_launcher)
        builder.setContentIntent(contentIntent)
        builder.setAutoCancel(true)
        builder.setLights(Color.BLUE, 500, 500)
        val pattern = longArrayOf(500, 500, 500, 500, 500)
        builder.setVibrate(pattern)
        builder.setStyle(NotificationCompat.InboxStyle())
        // Add as notification
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(1, builder.build())
    }
}