package com.btree.beekey.Controller.Adapter

import com.google.gson.annotations.SerializedName

class Message(
    @SerializedName("send_id") val send_id: String,
    @SerializedName("receive_id") val receive_id: String,
    @SerializedName("content") val content: String,
    @SerializedName("date_time") val date_time: String,
)