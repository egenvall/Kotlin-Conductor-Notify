package com.egenvall.skeppsklocka.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.egenvall.skeppsklocka.App
import com.egenvall.skeppsklocka.R
import kotlinx.android.synthetic.main.activity_main_kotlin.*


class MainActivity : AppCompatActivity() {
    lateinit var router : Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.graph.inject(this)
        setContentView(R.layout.activity_main_kotlin)

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
