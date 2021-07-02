package com.btree.beekey.Model

import com.google.gson.annotations.SerializedName

class SignUpPost (
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String,
    @SerializedName("phone") val message: String,
    @SerializedName("address") val address: String,
    @SerializedName("gender") val gender: Boolean
)
