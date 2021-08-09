package com.btree.beekey.Model

import com.google.gson.annotations.SerializedName

class SignUpBody (
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("email") val email: String
)
