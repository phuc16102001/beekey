package com.btree.beekey.Model

import com.google.gson.annotations.SerializedName

class ChangeInformationBody(
    @SerializedName("name") val name: String,
    @SerializedName("phone") val phone: String
)