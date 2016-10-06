package com.egenvall.skeppsklocka.ui

import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.VerticalChangeHandler
import com.egenvall.skeppsklocka.R
import org.jetbrains.anko.*

/**
 * View for displaying a received message in the Data payload from FCM
 */
class NotificationOpenController(message:String = "Standard") : Controller() {
    lateinit private var mMessage : String

    init {
            mMessage = message
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        return NotificationOpenControllerUI().createView(AnkoContext.create(inflater.context, this))
    }

    override fun handleBack(): Boolean {
        if(router.backstackSize < 2){
            super.handleBack()
            router.setRoot(RouterTransaction.with(MainController()).pushChangeHandler(VerticalChangeHandler()));
        }
        else{
            super.handleBack()
            router.popCurrentController()
        }
        return true
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
                textView(mMessage){
                    textSize = 30f
                    textColor = Color.WHITE
                }.lparams(width = wrapContent, height = wrapContent)
            }
        }
    }
}