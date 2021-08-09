package com.btree.beekey.Model

import com.google.gson.annotations.SerializedName

class LoginBody(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String
)
