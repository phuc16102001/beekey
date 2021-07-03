package com.btree.beekey.Model

import com.google.gson.annotations.SerializedName

class ChangePasswordResponse(
    @SerializedName("exitcode") val exitcode: Int,
    @SerializedName("message") val message: String
)