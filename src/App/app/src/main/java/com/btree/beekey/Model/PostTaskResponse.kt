package com.btree.beekey.Model

import com.google.gson.annotations.SerializedName

data class PostTaskResponse(
    @SerializedName("exitcode") val exitcode: Int,
    @SerializedName("message") val message: String
)