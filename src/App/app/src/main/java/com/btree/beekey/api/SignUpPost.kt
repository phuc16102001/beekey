package com.btree.beekey.api

import com.google.gson.annotations.SerializedName

class SignUpPost (
    @SerializedName("username") val username: String,
    @SerializedName("password") val token: String,
    @SerializedName("phone")val message: String,
    @SerializedName("address")val address: String,
    @SerializedName("gender")val gender: Boolean)
{
}