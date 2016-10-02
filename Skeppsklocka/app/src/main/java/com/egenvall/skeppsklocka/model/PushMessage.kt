package com.egenvall.skeppsklocka.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class PushMessage (val to:String = "/topics/push", var data : Data = Data("Standard Parameter")) {}
data class Data(val message: String){}
data class ServerResponse(@SerializedName("message_id") @Expose val responseMessage : String){}