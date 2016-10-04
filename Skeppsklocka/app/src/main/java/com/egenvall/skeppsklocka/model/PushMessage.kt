package com.egenvall.skeppsklocka.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class PushMessage (val to:String = "/topics/push",var data : Data = Data("This is a standard parameter for the message"))
                        {}
data class Data(val message: String){}

/*@SerializedName is necessary for GSON to interpret the JSON response correctly if we use variable names that differs from the JSON response
* The JSON object name will be exactly what is inside @SerializedName("exactly_this")
* */
data class ServerResponse(@SerializedName("message_id") @Expose val responseMessage : String
                          , @SerializedName("error") @Expose val errorMessage : String){}


