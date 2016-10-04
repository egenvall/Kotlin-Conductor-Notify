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

    /**
    * Through RxJava, ask our Network Interactor to perform an action (post a notification to firebase)
     * and subscribe to the result. Depending on if the action was successful or not, tell the view
     * to update with appropiate information
     *
     * */
    override fun sendPushNotification(message : String) {
        subscription = this.firebaseInteractor.postPushNotification(message)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            response ->
                            Log.d(TAG, "Response: ${response.responseMessage}")
                            view.pushNotificationCallback()
                        },
                        { err -> Log.d(TAG, "Error response") }
                )
    }

    override fun bindView(view: MainView) {
        this.view = view
    }

    override fun unbindView() {
        throw UnsupportedOperationException("not implemented")
    }

    override fun onDestroy() {
        if(!subscription.isUnsubscribed) subscription.unsubscribe()
    }
}