package com.btree.beekey.Model

import com.btree.beekey.Controller.Adapter.Message
import com.google.gson.annotations.SerializedName

class FetchMessageResponse(
    @SerializedName("exitcode") val exitcode: Int,
    @SerializedName("message") val message: String,
    @SerializedName("chats") val chats: List<Message>
)