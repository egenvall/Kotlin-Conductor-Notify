package com.egenvall.skeppsklocka.ui
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import org.jetbrains.anko.*

class PushedController:Controller() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        return PushedControllerUI().createView(AnkoContext.create(inflater.context, this))
    }

    inner class PushedControllerUI(): AnkoComponent<PushedController>{
        override fun createView(ui: AnkoContext<PushedController>) = with(ui) {
            verticalLayout{
                gravity = Gravity.CENTER
                backgroundColor = Color.GREEN

                textView("SUCCESS"){
                    textSize = 30f
                    textColor = Color.WHITE
                }.lparams(width = wrapContent, height = wrapContent)
            }
        }
    }
}