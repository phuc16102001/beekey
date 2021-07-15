package com.btree.beekey.Controller.Activity

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.btree.beekey.Controller.Adapter.TaskRequest
import com.btree.beekey.Controller.Adapter.TaskRequestAdapter
import com.btree.beekey.R

class MyListRequestActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_list_request)

        val TaskList = mutableListOf<TaskRequest>()

        TaskList.add(TaskRequest("Khoa", 50, "IT", "2001-01-01", "abcc"))
        TaskList.add(TaskRequest("Khanh", 60, "IT", "2001-01-01", "bdfdsfasdf"))
        TaskList.add(TaskRequest("Phuc", 70, "IT", "2001-01-01", "afdsfadsfa"))
        TaskList.add(TaskRequest("Thanh",80,"IT","2001-01-01","adfdafasdf"))
        Log.d("xxxxx",TaskList.toString())
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = TaskRequestAdapter(TaskList)

        recyclerView.setHasFixedSize(true)
    }
}
