package com.egenvall.skeppsklocka.presenter

import com.egenvall.skeppsklocka.ui.MvpView

interface Presenter<in V : MvpView> {

    fun bindView(view: V)

    fun unbindView()

    fun onDestroy()
}