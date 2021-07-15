package com.btree.beekey.Model

import com.google.gson.annotations.SerializedName

class GetInformationResponse (
    @SerializedName("exitcode") val exitcode: Int,
    @SerializedName("message") val message: String,
    @SerializedName("username") val username: String,
    @SerializedName("name") val displayName: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("email") val email: String,
    @SerializedName("coin") val coin: Int
)