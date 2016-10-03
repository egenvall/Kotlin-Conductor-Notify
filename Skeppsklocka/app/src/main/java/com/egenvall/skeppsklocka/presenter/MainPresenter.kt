package com.egenvall.skeppsklocka.presenter

import com.egenvall.skeppsklocka.ui.MainView


interface MainPresenter : Presenter<MainView>{
    fun sendPushNotification(message:String)
}