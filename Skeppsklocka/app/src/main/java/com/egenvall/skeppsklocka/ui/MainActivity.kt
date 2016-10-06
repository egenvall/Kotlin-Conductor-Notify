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
    lateinit var router : Router
    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.graph.inject(this)
        setContentView(R.layout.activity_main_kotlin)
        FirebaseMessaging.getInstance().subscribeToTopic("push")
        Log.d(TAG,"ONCREATE: ${FirebaseInstanceId.getInstance().getToken()}")

        router = Conductor.attachRouter(this, controller_container, savedInstanceState)
        if (!router.hasRootController()) {
            var bundle = checkBundleForMessage()
            if (bundle != null){
                router.setRoot(RouterTransaction.with(NotificationOpenController(bundle)));
            }
            else{
                router.setRoot(RouterTransaction.with(MainController()));

            }
        }
    }

    override fun onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed()
        }
    }

    fun transitionToNotificationOpenController(bundle: String){
        router.pushController(RouterTransaction.with(NotificationOpenController(bundle))
                .pushChangeHandler(VerticalChangeHandler())
                .popChangeHandler(VerticalChangeHandler()))
    }

    fun checkBundleForMessage() : String?{
        var extraMessage : String? = intent?.extras?.getString("message")
        if(extraMessage != null){
            Log.d(TAG,"${extraMessage}")
            intent.removeExtra("message") //HOW TO REMOVE THIS EXTRA
            return extraMessage
        }
        else{
            return null
        }
    }


    /**
     * Handle eventual passed Intent from FirebaseMessagingService
     * i.e Launch the controller responsible for displaying the FCM Message
     */
    override fun onResume(){
        super.onResume()
        val bundle = checkBundleForMessage()
        if (bundle != null){
            transitionToNotificationOpenController(bundle);
        }
    }



    /**
     * MainActivity is defined in AndroidManifest.xml as android:launchMode="singleTop"
     * onNewIntent acts as an entrypoint whenever an intent is received pointing to MainActivity,
     * since we don't want to launch a new instance of the Activity.
     */
    override fun onNewIntent(intent : Intent){
        /*BUG:
        * Scenarios in MainController(The Main View):
        * HOME Button is pressed: Activity is stopped and put to background, however the state is saved.
        * BACK Button is pressed: Activity is popped from stack and destroyed.
        * When
        *
        * BUG IS PROBABLY INTRODUCED BECAUSE MAINCONTROLLER IS PUSHED TO STACK BEFORE CHILDCONTROLLER IS CREATED
        *
        *
        * */
        Log.d(TAG,"ON NEW INTENT")
        setIntent(intent)
    }
}
