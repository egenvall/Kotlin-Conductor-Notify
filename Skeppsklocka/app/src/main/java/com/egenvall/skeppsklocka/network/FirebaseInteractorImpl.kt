package com.egenvall.skeppsklocka.network

import com.egenvall.skeppsklocka.model.Data
import com.egenvall.skeppsklocka.model.PushMessage
import com.egenvall.skeppsklocka.model.ServerResponse
import javax.inject.Inject
import rx.Observable



class FirebaseInteractorImpl @Inject constructor(val api: FirebaseService) : FirebaseInteractor {
    override fun postPushNotification(message: String) : Observable<ServerResponse> {
        val pushMessage = Data(message)
        var push = PushMessage()
        push.data = pushMessage
       return api.postPushNotification(push)
    }
}