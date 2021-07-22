package com.btree.beekey.Model

import com.google.gson.annotations.SerializedName

class ChangeInformationPost(
    @SerializedName("name") val name: String,
    @SerializedName("phone") val phone: String
)