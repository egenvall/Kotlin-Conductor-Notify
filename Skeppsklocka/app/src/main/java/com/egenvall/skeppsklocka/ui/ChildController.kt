package com.egenvall.skeppsklocka.ui

import android.graphics.Color
import android.graphics.Typeface
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler
import com.egenvall.skeppsklocka.App
import com.egenvall.skeppsklocka.Keyboards
import com.egenvall.skeppsklocka.R
import com.egenvall.skeppsklocka.changehandler.FlipChangeHandler
import com.egenvall.skeppsklocka.extensions.showSnackbar
import com.egenvall.skeppsklocka.presenter.MainPresenterImpl
import org.jetbrains.anko.*
import javax.inject.Inject

class ChildController: Controller(), MainView {
    val TAG = "Child"
    var mMessage = ""
    @Inject lateinit var mPresenter : MainPresenterImpl
    init {
        App.graph.inject(this)
        mPresenter.bindView(this)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        return ChildControllerUI().createView(AnkoContext.create(inflater.context, this))
    }

    fun messageUpdated(newText : String){
        mMessage = newText
    }

    override fun pushNotificationCallback(){
        router.pushController(RouterTransaction.with(PushedController())
                .pushChangeHandler(FlipChangeHandler())
                .popChangeHandler(FlipChangeHandler()))
    }
    fun childClicked(){
        Keyboards.hideKeyboard(view.context, view)
        view.showSnackbar("Notification sent")
        mPresenter.sendPushNotification(mMessage)
    }

    inner class ChildControllerUI() : AnkoComponent<ChildController> {
        override fun createView(ui: AnkoContext<ChildController>) = with(ui) {
            verticalLayout {
                gravity = Gravity.CENTER
                imageButton {
                    imageResource = R.drawable.large_bell_icon
                    scaleType = ImageView.ScaleType.CENTER_CROP
                    backgroundColor = Color.TRANSPARENT
                    onClick { Log.d(TAG, "CLICKED CHILD")
                    childClicked()
                    }
                }.lparams(width = 600, height = 600) {
                    bottomMargin = 25
                }
                editText(mMessage) {
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