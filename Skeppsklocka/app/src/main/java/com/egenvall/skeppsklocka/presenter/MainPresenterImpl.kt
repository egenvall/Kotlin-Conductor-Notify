package com.egenvall.skeppsklocka.presenter

import com.egenvall.skeppsklocka.ui.MainView

class MainPresenterImpl : MainPresenter {
    lateinit var view : MainView
    override fun sendPushNotification() {

        //Success?
        view.pushNotificationCallback()

    }

    override fun bindView(view: MainView) {
        this.view = view
    }

    override fun unbindView() {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDestroy() {

    }
}