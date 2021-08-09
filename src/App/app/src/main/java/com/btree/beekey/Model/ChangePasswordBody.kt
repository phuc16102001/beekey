package com.btree.beekey.Model

import com.google.gson.annotations.SerializedName

class ChangePasswordBody(
    @SerializedName("oldPassword") val oldPassword: String,
    @SerializedName("newPassword") val newPassword: String
)