package com.btree.beekey.Controller.Adapter

import com.google.gson.annotations.SerializedName
import java.util.*

class Task(
    @SerializedName("task_id") var task_id: Int,
    @SerializedName("title") var title: String,
    @SerializedName("description") var description: String,
    @SerializedName("offer") var offer: Int,
    @SerializedName("deadline") var deadline: Date,
    @SerializedName("category_id") var category_id: Int,
    @SerializedName("status") var status: Int,
    @SerializedName("lancer_id") var lancer_id: String,
    @SerializedName("user_id") var user_id: String,
) {
    companion object{
        const val TASK_PENDING=0
        const val TASK_DOING=1
        const val TASK_DONE=2
    }

    fun getStatusString() :String{
        when (status){
            TASK_PENDING -> return "PENDING"
            TASK_DOING -> return "ON-GOING"
            TASK_DONE -> return "DONE"
        }
        return "NOT FOUND"
    }
}