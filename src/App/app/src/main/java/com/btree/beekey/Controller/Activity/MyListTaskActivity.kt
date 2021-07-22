package com.btree.beekey.Controller.Activity

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.btree.beekey.Controller.Adapter.Tasks
import com.btree.beekey.Controller.Adapter.TasksAdapter
import com.btree.beekey.Model.getMyTaskResponse
import com.btree.beekey.R
import com.btree.beekey.Utils.Cache
import com.btree.beekey.Utils.MyAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyListTaskActivity:AppCompatActivity() {
    private lateinit var listTask: List<Tasks>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_list_task)
        getListRequest(this)
    }

    private fun loadAdapter(){
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = TasksAdapter(listTask)

        recyclerView.setHasFixedSize(true)
    }

    private fun getListRequest(context: Context){
        val token = Cache.getToken(context).toString()
        val response = MyAPI.getAPI().getMyTask(token)

        response.enqueue(object : Callback<getMyTaskResponse> {
            override fun onResponse(
                call: Call<getMyTaskResponse>,
                response: Response<getMyTaskResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    //Log.d("getMyRequest", data.toString())
                    if (data?.exitcode == 0) {
                        listTask = data.tasks
                        loadAdapter()
                    }
                }
            }

            override fun onFailure(call: Call<getMyTaskResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}
