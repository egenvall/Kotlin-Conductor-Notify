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


class MainActivity : AppCompatActivity() {
    lateinit var router : Router
    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.graph.inject(this)
        setContentView(R.layout.activity_main_kotlin)
        FirebaseMessaging.getInstance().subscribeToTopic("push")
        Log.d(TAG,"${FirebaseInstanceId.getInstance().getToken()}")

        router = Conductor.attachRouter(this, controller_container, savedInstanceState)
        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(MainController()));
        }
    }

    override fun onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed()
        }
    }

    fun transitionToNotificationOpenController(bundle: Bundle){
        router.pushController(RouterTransaction.with(NotificationOpenController(bundle))
                .pushChangeHandler(VerticalChangeHandler())
                .popChangeHandler(VerticalChangeHandler()))
    }

    override fun onPause(){
        super.onPause()
    }

    /**
     * Handle eventual passed Intent from FirebaseMessagingService
     * i.e Launch the controller responsible for displaying the FCM Message
     */
    override fun onResume(){
        super.onResume()
        val bundle : Bundle? = intent.extras
        if (bundle != null){
            transitionToNotificationOpenController(bundle)
        }
    }

    /**
     * MainActivity is defined in AndroidManifest.xml as android:launchMode="singleTop"
     * onNewIntent acts as an entrypoint whenever an intent is received pointing to MainActivity,
     * since we don't want to launch a new instance of the Activity.
     */
    override fun onNewIntent(intent : Intent){
        setIntent(intent)
    }
}
