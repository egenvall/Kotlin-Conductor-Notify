package com.egenvall.skeppsklocka.ui

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.VerticalChangeHandler
import com.egenvall.skeppsklocka.App
import com.egenvall.skeppsklocka.Keyboards
import com.egenvall.skeppsklocka.R
import com.egenvall.skeppsklocka.extensions.showSnackbar
import com.egenvall.skeppsklocka.presenter.MainPresenterImpl
import org.jetbrains.anko.*
import org.w3c.dom.Text
import javax.inject.Inject


class MainController(bundle : Bundle?) : Controller(), MainView{
    var message = ""
    var TAG = "MainController"
    var bundl : Bundle?
    @Inject lateinit var presenter : MainPresenterImpl


    init {
        if (bundle != null ){
            Log.d(TAG, "Received bundle: ${bundle.getString("message")}")
            bundl = bundle
        }
        else{
            Log.d(TAG,"BUNDLE WAS NULL")
            bundl = null
        }
        App.graph.inject(this)
        presenter.bindView(this)
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        if (bundl != null){
            transitionToNotificationOpenController(bundl)
        }
        return MainControllerUI().createView(AnkoContext.create(inflater.context, this))
    }

    fun messageUpdated(newText : String){
        message = newText
    }

    fun onClockClicked() {
        Keyboards.hideKeyboard(view.context, view)
        view.showSnackbar("Notification sent")
        presenter.sendPushNotification(message)
    }

    fun transitionToNotificationOpenController(bundl: Bundle?){
        router.pushController(RouterTransaction.with(NotificationOpenController(bundl))
                .pushChangeHandler(VerticalChangeHandler())
                .popChangeHandler(VerticalChangeHandler()))
    }
    override fun pushNotificationCallback(){
        router.pushController(RouterTransaction.with(PushedController())
                .pushChangeHandler(VerticalChangeHandler())
                .popChangeHandler(VerticalChangeHandler()))
    }

    inner class MainControllerUI() : AnkoComponent<MainController> {
        override fun createView(ui: AnkoContext<MainController>) = with(ui) {
            verticalLayout {
                gravity = Gravity.CENTER

                imageButton{
                    imageResource = R.drawable.skeppsklocka
                    scaleType = ImageView.ScaleType.CENTER_CROP
                    onClick { onClockClicked() }
                }.lparams(width = 600, height = 600)

                editText(message) {
                    hint = "Meddelande"
                    imeOptions = EditorInfo.IME_ACTION_NEXT
                    singleLine = true
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