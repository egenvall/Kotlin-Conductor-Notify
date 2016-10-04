package com.egenvall.skeppsklocka.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import com.bluelinelabs.conductor.*
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
        val startingIntent : Intent? = intent
        //Awesome about Kotlin, won't give Nullpointer Exception even if passed intent is null
        App.graph.inject(this)
        setContentView(R.layout.activity_main_kotlin)
        FirebaseMessaging.getInstance().subscribeToTopic("push")
        Log.d(TAG,"${FirebaseInstanceId.getInstance().getToken()}")

        //Null if no message is passed
        val bundleMessage = startingIntent?.getStringExtra("message")
        Log.d(TAG,"Starting intent message: ${bundleMessage}")

        router = Conductor.attachRouter(this, controller_container, savedInstanceState)

        if(router != null){
            Log.d(TAG,"ROUTER NOT NULL")
        }
        else{
            Log.d(TAG,"ROUTER NULL")
        }
            if (!router.hasRootController()) {
                Log.d(TAG,"ATTATCHING ROUTER")
                router.setRoot(RouterTransaction.with(MainController(startingIntent?.extras)));
            }
    }

    override fun onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed()
        }
    }
}
