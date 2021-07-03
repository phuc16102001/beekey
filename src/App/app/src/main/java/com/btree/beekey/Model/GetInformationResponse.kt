package com.btree.beekey.Model

import com.google.gson.annotations.SerializedName

class GetInformationResponse (
    @SerializedName("exitcode") val exitcode: Int,
    @SerializedName("username") val username: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("phone") val phone: Boolean,
    @SerializedName("address") val address: String,
    @SerializedName("message") val message: String,
)
{
}