package com.btree.beekey.Controller.Activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.btree.beekey.Controller.Adapter.*
import com.btree.beekey.Model.GetInformationResponse
import com.btree.beekey.Model.getMyRequestResponse
import com.btree.beekey.R
import com.btree.beekey.Utils.Cache
import com.btree.beekey.Utils.MyAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyListRequestActivity:AppCompatActivity() {
    private var TasksListAPI: List<Tasks>? = null
    private var TaskList = mutableListOf<Tasks>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_list_request)


        getListRequest(this)

        Log.d("MyListRequestActivity",TaskList.toString())

    }
    
    private fun loadAdapter(){
        if (TasksListAPI == null) {
            return
        }
        for (task in TasksListAPI!!) {
            Log.d("abbbccc", task.description)
            TaskList.add(task)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = TasksAdapter(TaskList)

        recyclerView.setHasFixedSize(true)
    }

    private fun getListRequest(context: Context){
        val token = Cache.getToken(context).toString()
        val response = MyAPI.getAPI().getMyRequest(token)

        response.enqueue(object : Callback<getMyRequestResponse>{
            override fun onResponse(
                call: Call<getMyRequestResponse>,
                response: Response<getMyRequestResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    //Log.d("getMyRequest", data.toString())
                    if (data?.exitcode == 0) {
                        TasksListAPI = data.tasks
                        loadAdapter()
                    }
                }
            }

            override fun onFailure(call: Call<getMyRequestResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}
