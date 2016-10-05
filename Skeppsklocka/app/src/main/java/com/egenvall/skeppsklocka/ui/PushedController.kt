package com.egenvall.skeppsklocka.ui
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bluelinelabs.conductor.Controller
import com.egenvall.skeppsklocka.R
import org.jetbrains.anko.*

class PushedController:Controller() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        return PushedControllerUI().createView(AnkoContext.create(inflater.context, this))
    }

    inner class PushedControllerUI(): AnkoComponent<PushedController>{
        override fun createView(ui: AnkoContext<PushedController>) = with(ui) {
            verticalLayout{
                gravity = Gravity.CENTER
                backgroundResource = R.color.colorPrimaryLight

                textView("MESSAGE SENT"){
                    textSize = 30f
                    textColor = Color.WHITE
                    backgroundColor = Color.argb(54,235,123,82)
                    topPadding = 50
                    bottomPadding = 50
                    leftPadding = 30
                    rightPadding = 30
                    setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                }.lparams(width = wrapContent, height = wrapContent)
            }
        }
    }
}