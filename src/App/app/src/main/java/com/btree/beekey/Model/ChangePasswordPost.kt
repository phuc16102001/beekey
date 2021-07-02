package com.btree.beekey.Model

import com.google.gson.annotations.SerializedName

class ChangePasswordPost
    (@SerializedName("oldPassword") val oldPassword: String,
     @SerializedName("newPassword") val newPassword: String)