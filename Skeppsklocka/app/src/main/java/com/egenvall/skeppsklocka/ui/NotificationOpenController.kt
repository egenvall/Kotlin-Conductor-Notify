package com.egenvall.skeppsklocka.ui

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bluelinelabs.conductor.Controller
import com.egenvall.skeppsklocka.R
import com.egenvall.skeppsklocka.util.BundleBuilder
import org.jetbrains.anko.*

/**
 * View for displaying a received message in the Data payload from FCM
 */
class NotificationOpenController(bundle: Bundle) : Controller(bundle) {
    lateinit private var mMessage : String

    init {
        var receivedMessage = bundle.getString("message")
        if (receivedMessage != null){
            mMessage = receivedMessage
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        return NotificationOpenControllerUI().createView(AnkoContext.create(inflater.context, this))
    }

    /**
     * Define layout through Anko DSL.
     * Contains a textView with the text of @mMessage
     */
    inner class NotificationOpenControllerUI(): AnkoComponent<NotificationOpenController> {
        override fun createView(ui: AnkoContext<NotificationOpenController>) = with(ui) {
            verticalLayout{
                gravity = Gravity.CENTER
                backgroundResource = R.color.colorPrimaryDark

                /*imageView(){
                    imageResource = R.drawable.large_first_place_medal
                    scaleType = ImageView.ScaleType.CENTER_CROP
                    backgroundColor = Color.TRANSPARENT
                }.lparams(width = 600, height = 600){
                    bottomMargin = 25
                }*/

                textView(mMessage){
                    textSize = 30f
                    textColor = Color.WHITE
                }.lparams(width = wrapContent, height = wrapContent)
            }
        }
    }
}