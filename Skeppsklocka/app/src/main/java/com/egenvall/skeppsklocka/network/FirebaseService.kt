package com.egenvall.skeppsklocka.network

import com.egenvall.skeppsklocka.model.PushMessage
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable

interface FirebaseService {
    @POST("fcm/send")
    fun postPushNotification(@Body pushMessage : PushMessage) : Observable<Boolean>
}