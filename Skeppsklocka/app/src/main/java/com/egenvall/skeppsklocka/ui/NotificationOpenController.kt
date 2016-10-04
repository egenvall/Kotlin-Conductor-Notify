package com.egenvall.skeppsklocka.ui

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.egenvall.skeppsklocka.util.BundleBuilder
import org.jetbrains.anko.*


class NotificationOpenController(bundle: Bundle?) : Controller(bundle) {
    lateinit var message : String

    init {
        var string :String? = bundle?.getString("message")
        if (string != null){
            message = string
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        return NotificationOpenControllerUI().createView(AnkoContext.create(inflater.context, this))
    }

    inner class NotificationOpenControllerUI(): AnkoComponent<NotificationOpenController> {
        override fun createView(ui: AnkoContext<NotificationOpenController>) = with(ui) {
            verticalLayout{
                gravity = Gravity.CENTER
                backgroundColor = Color.RED

                textView(message){
                    textSize = 30f
                    textColor = Color.WHITE
                }.lparams(width = wrapContent, height = wrapContent)
            }
        }
    }
}