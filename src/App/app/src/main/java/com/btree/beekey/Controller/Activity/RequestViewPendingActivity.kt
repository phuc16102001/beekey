package com.btree.beekey.Controller.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.btree.beekey.Controller.Adapter.Task
import com.btree.beekey.Model.TaskDetailBody
import com.btree.beekey.Model.TaskDetailResponse
import com.btree.beekey.Utils.Cache
import com.btree.beekey.Utils.DateFormat.Companion.dateformat
import com.btree.beekey.Utils.MyAPI
import com.btree.beekey.databinding.ActivityRequestViewPendingBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RequestViewPendingActivity : AppCompatActivity() {
    private var task_id = -1
    private lateinit var binding: ActivityRequestViewPendingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRequestViewPendingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        task_id = intent.getIntExtra("task_id",-1)
        if (task_id==-1){
            Toast.makeText(this,"Error loading task", Toast.LENGTH_SHORT).show()
            finish()
        }

        getTask(this)

        binding.btnViewOffer.setOnClickListener {
            btnViewOfferClick(this)
        }
    }

    override fun onResume() {
        getTask(this)

        super.onResume()
    }

    private fun btnViewOfferClick(context:Context) {
        val intent = Intent(context, ViewCounterOfferListActivity::class.java)
        intent.putExtra("task_id", task_id)
        startActivity(intent)
    }

    private fun loadScreen(task: Task){
        binding.txtTitle.text =  task.title
        binding.txtDeadline.text =  task.deadline.dateformat()
        binding.txtOffer.text =  task.offer.toString()
        binding.txtStatus.text =  task.getStatusString()
        binding.txtDescription.text =  task.description
    }

    private fun getTask(context: Context){
        val token = Cache.getToken(context).toString()
        val response = MyAPI.getAPI().postViewTaskDetail(token, TaskDetailBody(task_id.toString()))

        response.enqueue(object : Callback<TaskDetailResponse> {
            override fun onResponse(
                call: Call<TaskDetailResponse>,
                response: Response<TaskDetailResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data?.exitcode!= 0) {
                        finish()
                    }
                    loadScreen(data!!.task)
                }
            }

            override fun onFailure(call: Call<TaskDetailResponse>, t: Throwable) {
                Toast.makeText(context, "Fail", Toast.LENGTH_LONG).show()
            }
        })
    }
}