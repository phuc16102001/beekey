package com.btree.beekey.api

import com.google.gson.annotations.SerializedName

class LoginPost
    (@SerializedName("username") val username: String,
    @SerializedName("password") val password: String)
{
}