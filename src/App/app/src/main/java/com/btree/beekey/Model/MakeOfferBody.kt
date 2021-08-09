package com.btree.beekey.Model

import com.google.gson.annotations.SerializedName

class MakeOfferBody(
    @SerializedName("task_id") val task_id: Int,
    @SerializedName("reason") val reason: String,
    @SerializedName("offer") val offer: Int
)