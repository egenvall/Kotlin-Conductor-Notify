package com.egenvall.skeppsklocka.network
import rx.Observable


interface FirebaseInteractor {
    fun postPushNotification(message:String) : Observable<Boolean>
}