package com.egenvall.skeppsklocka.network

import com.egenvall.skeppsklocka.model.Data
import com.egenvall.skeppsklocka.model.Notification
import com.egenvall.skeppsklocka.model.PushMessage
import com.egenvall.skeppsklocka.model.ServerResponse
import javax.inject.Inject
import rx.Observable

/**
 * PushMessage() JSON Request looks like
 *
 * {
    "to" : "BASEURL/topics/push",
    "notification" : {
        "body" : "Standard Body",
        "title" : "Standard Title"
    },
    "data" : {
        "message" : "Standard Parameter"
    }
}

 If we specify a message, "Standard Parameter" will be replaced by custom message
 */


class FirebaseInteractorImpl @Inject constructor(val api: FirebaseService) : FirebaseInteractor {
    override fun postPushNotification(message: String): Observable<ServerResponse> {
        //PushMessage has three Default Arguments, so we don't need to specify parameters if we don't
        //want to post a message.

        //TODO How to separate in controller if message is empty

        //if message is empty we can do PushMessage() since PushMessage has default arguments
        //However if we specify a Data we need to specify a message

        //Use Named Parameters to set the data parameter to a non-default value
        var push = PushMessage(data = Data(message))
        return api.postPushNotification(push)
    }
}