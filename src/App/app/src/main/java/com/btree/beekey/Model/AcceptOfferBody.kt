package com.btree.beekey.Model

import com.google.gson.annotations.SerializedName

class AcceptOfferBody (
    @SerializedName("task_id") val task_id : Int,
    @SerializedName("lancer_id") val lancer_id: String
)
