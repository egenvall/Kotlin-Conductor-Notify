package com.egenvall.skeppsklocka.presenter

import android.util.Log
import com.egenvall.skeppsklocka.network.FirebaseInteractor
import com.egenvall.skeppsklocka.ui.MainView
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

class MainPresenterImpl @Inject constructor(firebaseInteractor: FirebaseInteractor) : MainPresenter {
    val TAG  = "MainPresenter"
    lateinit var view : MainView
    lateinit var firebaseInteractor : FirebaseInteractor
    lateinit var subscription : Subscription

    init {
        this.firebaseInteractor = firebaseInteractor
    }


    override fun sendPushNotification() {
        subscription = this.firebaseInteractor.postPushNotification("Test")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            response ->
                            Log.d(TAG, "Response: " + response.responseMessage)
                            view.pushNotificationCallback()
                        },
                        { err -> Log.d(TAG, "Error response") }
                )
    }

    override fun bindView(view: MainView) {
        this.view = view
    }

    override fun unbindView() {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDestroy() {
        if(!subscription.isUnsubscribed) subscription.unsubscribe()
    }
}