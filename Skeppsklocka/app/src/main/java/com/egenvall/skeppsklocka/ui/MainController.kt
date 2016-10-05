package com.egenvall.skeppsklocka.ui

import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ImageButton
import android.widget.ImageView
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler
import com.bluelinelabs.conductor.changehandler.VerticalChangeHandler
import com.bumptech.glide.Glide
import com.egenvall.skeppsklocka.App
import com.egenvall.skeppsklocka.Keyboards
import com.egenvall.skeppsklocka.R
import com.egenvall.skeppsklocka.extensions.showSnackbar
import com.egenvall.skeppsklocka.presenter.MainPresenterImpl
import org.jetbrains.anko.*
import kotlinx.android.synthetic.main.activity_main_kotlin.controller_container

import javax.inject.Inject

/**
 * View containing a TextField (message) and an ImageButton (ship's bell).
 * When ImageButton is pressed, ask presenter to sendNotification(message)
 */
class  MainController : Controller(), MainView{
    var message = ""
    var TAG = "MainController"
    @Inject lateinit var presenter : MainPresenterImpl

    init {
        App.graph.inject(this)
        presenter.bindView(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        return MainControllerUI().createView(AnkoContext.create(inflater.context, this))
    }

    override fun onDestroy(){
        super.onDestroy()
    }

    fun messageUpdated(newText : String){
        message = newText
    }

    fun onClockClicked() {
        Keyboards.hideKeyboard(view.context, view)
        view.showSnackbar("Notification sent")
        presenter.sendPushNotification(message)
    }
    override fun pushNotificationCallback(){
        router.pushController(RouterTransaction.with(PushedController())
                .pushChangeHandler(FadeChangeHandler())
                .popChangeHandler(FadeChangeHandler()))
    }

    inner class MainControllerUI() : AnkoComponent<MainController> {
        override fun createView(ui: AnkoContext<MainController>) = with(ui) {
            verticalLayout {
                gravity = Gravity.CENTER
                backgroundResource = R.drawable.background
                //backgroundResource = R.color.colorPrimary

                /* Linkback to http://www.kameleon.pics required, no commercial use*/
                imageButton{
                    imageResource = R.drawable.large_bell_icon
                    scaleType = ImageView.ScaleType.CENTER_CROP
                    backgroundColor = Color.TRANSPARENT
                    onClick { onClockClicked() }
                }.lparams(width = 600, height = 600){
                    bottomMargin = 25
                }

                editText(message) {
                    hintTextColor = Color.WHITE
                    textColor = Color.WHITE
                    hint = "WRITE YOUR MESSAGE HERE"
                    imeOptions = EditorInfo.IME_ACTION_NEXT
                    singleLine = true
                    setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                    textChangedListener {
                        onTextChanged { text, start, before, count ->
                            messageUpdated(text.toString())
                        }
                    }
                }.lparams(width = wrapContent, height = wrapContent)
            }
        }
    }
}