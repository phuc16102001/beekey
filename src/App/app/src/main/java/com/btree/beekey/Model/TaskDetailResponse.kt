package com.btree.beekey.Model

import com.btree.beekey.Controller.Adapter.Task
import com.google.gson.annotations.SerializedName

class TaskDetailResponse (
    @SerializedName("exitcode") val exitcode:Int,
    @SerializedName("message") val message:String,
    @SerializedName("task") val task: Task
)
