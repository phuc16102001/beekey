package com.btree.beekey.Model

import com.google.gson.annotations.SerializedName

class LoginPost(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String
)
