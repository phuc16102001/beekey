package com.btree.beekey.Model

import com.btree.beekey.Controller.Adapter.Feedback
import com.google.gson.annotations.SerializedName

class ListFeedbackResponse (
    @SerializedName("exitcode") val exitcode: Int,
    @SerializedName("message") val message: String,
    @SerializedName("feedbacks") val feedbacks: List<Feedback>
)

