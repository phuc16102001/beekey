package com.btree.beekey.Model

import com.google.gson.annotations.SerializedName

class TopUpBody(
    @SerializedName("topUpValue") val topUpValue: Int
)