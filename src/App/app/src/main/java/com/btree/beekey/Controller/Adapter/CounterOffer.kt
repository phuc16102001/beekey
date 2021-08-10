package com.btree.beekey.Controller.Adapter

import com.google.gson.annotations.SerializedName

class CounterOffer (
    @SerializedName("task_id") val taskId : Int,
    @SerializedName("lancer_id") val lancerId: Int,
    @SerializedName("reason") val reason: String,
    @SerializedName("offer") val offer: Int
)