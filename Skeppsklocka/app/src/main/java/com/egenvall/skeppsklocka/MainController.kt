package com.egenvall.skeppsklocka

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import com.bluelinelabs.conductor.Controller
import org.jetbrains.anko.*


class MainController : Controller(){
    var message = ""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        return MainControllerUI().createView(AnkoContext.create(inflater.context, this))
    }

    fun messageUpdated(newText : String){
        message = newText
    }

    fun onLoginClicked() {
        Keyboards.hideKeyboard(view.context, view)
        //Presenter.
        return
    }

    inner class MainControllerUI() : AnkoComponent<MainController> {
        override fun createView(ui: AnkoContext<MainController>) = with(ui) {
            verticalLayout {
                gravity = Gravity.CENTER
                imageButton{
                    imageResource = R.drawable.skeppsklocka
                    scaleType = ImageView.ScaleType.CENTER_CROP
                    onClick { onLoginClicked() }
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