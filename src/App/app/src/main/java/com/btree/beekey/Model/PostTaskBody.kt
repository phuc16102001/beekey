package com.btree.beekey.Model

import com.google.gson.annotations.SerializedName
import java.util.*

class PostTaskBody(
    @SerializedName("title") val title: String,
    @SerializedName("deadline") val deadline: String,
    @SerializedName("offer") val offer: Int,
    @SerializedName("description") val description: String,
    @SerializedName("category_id") val category_id: String,
    )
