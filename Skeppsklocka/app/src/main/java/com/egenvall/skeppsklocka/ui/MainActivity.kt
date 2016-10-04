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
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper


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

        if(startingIntent?.extras != null){
            transitionToNotificationOpenController(startingIntent?.extras)
        }
        else{
            if(router != null){
                Log.d(TAG,"ROUTER NOT NULL")
            }
            else{
                Log.d(TAG,"ROUTER NULL")
            }
            if (!router.hasRootController()) {
                Log.d(TAG,"ATTATCHING ROUTER")
                router.setRoot(RouterTransaction.with(MainController()));
            }
        }


    }

    override fun onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed()
        }
    }

    fun transitionToNotificationOpenController(bundl: Bundle?){
        Log.d(TAG,""+router.backstack.toString())
        router.pushController(RouterTransaction.with(NotificationOpenController(bundl))
                .pushChangeHandler(VerticalChangeHandler())
                .popChangeHandler(VerticalChangeHandler()))
        Log.d(TAG,""+router.backstack.toString())

    }
}
