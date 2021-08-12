package com.btree.beekey.Model

import com.google.gson.annotations.SerializedName

class PostFeedbackBody(
    @SerializedName("title") val title: String,
    @SerializedName("task_id") val task_id: Int,
    @SerializedName("description") val description: String
)