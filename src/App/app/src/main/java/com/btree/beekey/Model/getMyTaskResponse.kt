package com.btree.beekey.Model

import com.btree.beekey.Controller.Adapter.Tasks
import com.google.gson.annotations.SerializedName

class getMyTaskResponse(@SerializedName("exitcode") val exitcode: Int,
                        @SerializedName("message") val message: String,
                        @SerializedName("tasks") val tasks: List<Tasks>,
)
{}