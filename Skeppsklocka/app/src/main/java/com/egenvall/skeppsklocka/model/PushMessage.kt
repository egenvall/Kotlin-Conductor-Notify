package com.egenvall.skeppsklocka.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class PushMessage (val to:String = "/topics/push"/*, var notification : Notification = Notification() */,var data : Data = Data("Standard Parameter"))
                        {}
data class Data(val message: String){}
data class Notification(val body:String = "Standard body", val title : String = "Standard title", val sound : String = "default")
data class ServerResponse(@SerializedName("message_id") @Expose val responseMessage : String
                          , @SerializedName("error") @Expose val errorMessage : String){}


