package com.egenvall.skeppsklocka.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.egenvall.skeppsklocka.App
import com.egenvall.skeppsklocka.R
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_main_kotlin.*


class MainActivity : AppCompatActivity() {
    lateinit var router : Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.graph.inject(this)
        setContentView(R.layout.activity_main_kotlin)
        FirebaseMessaging.getInstance().subscribeToTopic("push")
        Log.d("Main",""+FirebaseInstanceId.getInstance().getToken())


        router = Conductor.attachRouter(this, controller_container, savedInstanceState);
        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(MainController()));
        }
    }

    override fun onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed()
        }
    }
}
