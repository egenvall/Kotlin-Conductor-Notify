package com.egenvall.skeppsklocka.model


data class PushMessage (val to:String = "/topics/push", var data : Data = Data("Standard Parameter")) {}
data class Data(val message: String){}