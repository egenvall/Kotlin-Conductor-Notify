package com.egenvall.skeppsklocka.network

import com.egenvall.skeppsklocka.model.Data
import com.egenvall.skeppsklocka.model.PushMessage
import com.egenvall.skeppsklocka.model.ServerResponse
import rx.Observable
import javax.inject.Inject

class FirebaseInteractorImpl @Inject constructor(val api: FirebaseService) : FirebaseInteractor {
    override fun postPushNotification(message: String): Observable<ServerResponse> {
        return api.postPushNotification(PushMessage(data = Data(message)))
    }
}