package com.egenvall.skeppsklocka.ui
import android.graphics.Color
import android.graphics.Typeface
import android.util.Log
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
            verticalLayout {
                gravity = Gravity.CENTER
                //backgroundResource = R.drawable.preview
                imageButton {
                    imageResource = R.drawable.large_paper_plance
                    scaleType = ImageView.ScaleType.CENTER_CROP
                    backgroundColor = Color.TRANSPARENT
                    onClick { router.popController(this@PushedController) }
                }.lparams(width = 600, height = 600) {
                    bottomMargin = 25
                }
            }
        }
    }
}