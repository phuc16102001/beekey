package com.btree.beekey.Model

import com.google.gson.annotations.SerializedName

class ReportResponse(
    @SerializedName("exitcode") val exitcode: Int,
    @SerializedName("message") val message: String
)