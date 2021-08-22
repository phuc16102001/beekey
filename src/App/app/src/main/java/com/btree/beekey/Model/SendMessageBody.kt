package com.btree.beekey.Model

import com.google.gson.annotations.SerializedName

class SendMessageBody (
    @SerializedName("receive_id") val receive_id: String,
    @SerializedName("content") val content: String
)
