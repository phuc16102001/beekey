package com.btree.beekey.Controller.Adapter

import com.google.gson.annotations.SerializedName

class Feedback (
    @SerializedName("title") var title: String,
    @SerializedName("feedback_id") var feedbackID: Int,
    @SerializedName("description") var description: String,
    @SerializedName("user_id") var userID: String
)