package com.btree.beekey.Controller.Activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.btree.beekey.Controller.Adapter.Task
import com.btree.beekey.Model.MakeOfferBody
import com.btree.beekey.Model.MakeOfferResponse
import com.btree.beekey.Model.TaskDetailBody
import com.btree.beekey.Model.TaskDetailResponse
import com.btree.beekey.Utils.Cache
import com.btree.beekey.Utils.DateFormat.Companion.dateformat
import com.btree.beekey.Utils.MyAPI
import com.btree.beekey.databinding.ActivityCounterOfferBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CounterOfferActivity : AppCompatActivity() {
    private var TAG = "CounterOfferActivity"  //for debug
    private var task_id = -1
    private lateinit var binding:ActivityCounterOfferBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCounterOfferBinding.inflate(layoutInflater)
        setContentView(binding.root)

        task_id = intent.getIntExtra("task_id",-1)
        if (task_id==-1){
            Toast.makeText(this,"Error loading task",Toast.LENGTH_SHORT).show()
            finish()
        }
        binding.btnSend.setOnClickListener{
            clickSendBtn(this)
        }
        getTask(this)
    }

    private fun loadTask(context: Context, task:Task) {
        binding.txtTitle.text = task.title
        binding.txtDescription.text = task.description
        binding.txtDeadline.text = task.deadline.dateformat()
        binding.txtOffer.text = task.offer.toString()
        binding.txtStatus.text = task.getStatusString()
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
                    loadTask(context,data!!.task)
                }
            }

            override fun onFailure(call: Call<TaskDetailResponse>, t: Throwable) {
                Toast.makeText(context, "Fail", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun clickSendBtn(context: Context) {
        val reason = binding.edtReason.text.toString()
        val offer = binding.edtOfferPrice.text.toString().toIntOrNull()
        if (offer==null) {
            Toast.makeText(context,"Cannot leave field blank",Toast.LENGTH_SHORT).show()
            return
        }

        val token = Cache.getToken(this).toString()
        val response = MyAPI.getAPI().postCounterOffer(token, MakeOfferBody(task_id,reason,offer))
        Log.d("CounterOfferStatus:", taskId.toString())

        response.enqueue(object : Callback<MakeOfferResponse> {
            override fun onResponse(
                call: Call<MakeOfferResponse>,
                response: Response<MakeOfferResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    Log.d("CounterOfferStatus", data.toString())
                    if (data?.exitcode == 0) {
                        finish()
                    }
                    else if (data?.exitcode == 104){
                        Toast.makeText(context, data.message, Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<MakeOfferResponse>, t: Throwable) {
                Toast.makeText(context, "Fail", Toast.LENGTH_LONG).show()
            }
        })
    }
}