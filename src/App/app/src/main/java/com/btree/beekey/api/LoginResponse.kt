package com.btree.beekey.api

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("exitcode") val exitcode: Int,
    @SerializedName("token") val token: String,
    @SerializedName("message")val message: String
) {
}