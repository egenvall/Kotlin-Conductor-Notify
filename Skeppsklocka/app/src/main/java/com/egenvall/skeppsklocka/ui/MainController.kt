package com.egenvall.skeppsklocka.ui

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.RouterTransaction
import com.egenvall.skeppsklocka.R
import org.jetbrains.anko.*

class  MainController : Controller(){
    var TAG = "MainController"
    lateinit var mFrame :FrameLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        return MainControllerUI().createView(AnkoContext.create(inflater.context, this))
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        val childRouter = getChildRouter(mFrame, null).setPopsLastView(false)
        if (!childRouter.hasRootController()) {
            childRouter.setRoot(RouterTransaction.with(ChildController()))
        }
    }
    inner class MainControllerUI() : AnkoComponent<MainController> {
        override fun createView(ui: AnkoContext<MainController>) = with(ui) {
            verticalLayout {
                gravity = Gravity.CENTER
                backgroundResource = R.drawable.background
                mFrame = frameLayout{}
            }
        }
    }
}