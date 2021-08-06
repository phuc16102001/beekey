package com.btree.beekey.Model

import com.btree.beekey.Controller.Adapter.Task
import com.google.gson.annotations.SerializedName

class GetMyTaskResponse(
    @SerializedName("exitcode") val exitcode: Int,
    @SerializedName("message") val message: String,
    @SerializedName("tasks") val tasks: List<Task>,
)
{}