package com.btree.beekey.Controller.Activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.btree.beekey.Controller.Adapter.ItemClickListener
import com.btree.beekey.Controller.Adapter.Task
import com.btree.beekey.Controller.Adapter.TaskAdapter
import com.btree.beekey.Model.ListTaskResponse
import com.btree.beekey.R
import com.btree.beekey.Utils.Cache
import com.btree.beekey.Utils.MyAPI
import com.btree.beekey.databinding.ActivityMyListRequestBinding
import com.btree.beekey.databinding.ActivityMyListTaskBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyListTaskActivity:AppCompatActivity() {
    private lateinit var listTask: List<Task>
    private lateinit var binding: ActivityMyListTaskBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyListTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getListRequest(this)
    }

    override fun onResume() {
        getListRequest(this)

        super.onResume()
    }

    private fun loadAdapter(context: Context){
        val taskAdapter = TaskAdapter(listTask)
        taskAdapter.setClickListener(object : ItemClickListener {
            override fun onClick(view: View, position: Int) {
                val intent = Intent(context,TaskViewActivity::class.java)
                intent.putExtra("task_id",listTask[position].task_id)
                startActivity(intent)
            }
        })
        binding.listTask.adapter = taskAdapter
        if (listTask.size==0) {
            Toast.makeText(context,"You have not achieved any task",Toast.LENGTH_SHORT).show()
        }
    }

    private fun getListRequest(context: Context){
        val token = Cache.getToken(context).toString()
        val response = MyAPI.getAPI().getMyTask(token)

        response.enqueue(object : Callback<ListTaskResponse> {
            override fun onResponse(
                call: Call<ListTaskResponse>,
                response: Response<ListTaskResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data?.exitcode == 0) {
                        listTask = data.tasks
                        loadAdapter(context)
                    }
                }
            }

            override fun onFailure(call: Call<ListTaskResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}
