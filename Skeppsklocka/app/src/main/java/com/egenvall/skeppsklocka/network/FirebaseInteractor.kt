package com.egenvall.skeppsklocka.network
import com.egenvall.skeppsklocka.model.ServerResponse
import rx.Observable


interface FirebaseInteractor {
    fun postPushNotification(message:String) : Observable<ServerResponse>
}