package com.egenvall.skeppsklocka.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.VerticalChangeHandler
import com.egenvall.skeppsklocka.App
import com.egenvall.skeppsklocka.R
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_main_kotlin.*
import org.jetbrains.anko.clearTop


class MainActivity : AppCompatActivity() {
    lateinit var mRouter : Router
    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.graph.inject(this)
        setContentView(R.layout.activity_main_kotlin)
        FirebaseMessaging.getInstance().subscribeToTopic("push")
        mRouter = Conductor.attachRouter(this, controller_container, savedInstanceState)
        if (!mRouter.hasRootController()) {
            var notificationMessage = checkBundleForMessage()
            if (notificationMessage != null){
                mRouter.setRoot(RouterTransaction.with(NotificationOpenController(notificationMessage)));
            }
            else{
                mRouter.setRoot(RouterTransaction.with(MainController()));

            }
        }
    }

    override fun onBackPressed() {
        if (!mRouter.handleBack()) {
            super.onBackPressed()
        }
    }

    fun transitionToNotificationOpenController(bundle: String){
        mRouter.pushController(RouterTransaction.with(NotificationOpenController(bundle))
                .pushChangeHandler(VerticalChangeHandler())
                .popChangeHandler(VerticalChangeHandler()))
    }

    fun checkBundleForMessage() : String?{
        val notificationMessage = intent?.getStringExtra("message")
        intent.removeExtra("message")
        return notificationMessage ?: null
    }


    /**
     * Handle eventual passed Intent from FirebaseMessagingService
     * i.e Launch the controller responsible for displaying the FCM Message when we dont
     * have to create an activity.
     */
    override fun onResume(){
        super.onResume()
        val notificationMessage = checkBundleForMessage()
        if (notificationMessage != null){
            transitionToNotificationOpenController(notificationMessage);
        }
    }



    /**
     * MainActivity is defined in AndroidManifest.xml as android:launchMode="singleTop"
     * onNewIntent acts as an entrypoint whenever an intent is received while MainActivity is already created.
     *
     */
    override fun onNewIntent(intent : Intent){
        Log.d(TAG,"ON NEW INTENT")
        setIntent(intent)
    }
}
