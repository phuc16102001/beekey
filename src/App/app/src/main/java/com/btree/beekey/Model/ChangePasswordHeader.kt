package com.btree.beekey.Model

import com.google.gson.annotations.SerializedName

class ChangePasswordHeader
    (@SerializedName("token") val token: String)