package com.btree.beekey.Controller.Activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.btree.beekey.Controller.Adapter.Task
import com.btree.beekey.Model.*
import com.btree.beekey.R
import com.btree.beekey.Utils.Cache
import com.btree.beekey.Utils.DateFormat.Companion.dateformat
import com.btree.beekey.Utils.MyAPI
import com.btree.beekey.databinding.ActivityRequestViewDoingBinding
import com.btree.beekey.databinding.ActivityRequestViewPendingBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RequestViewDoingActivity : AppCompatActivity() {
    private var task_id = -1
    private lateinit var binding: ActivityRequestViewDoingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRequestViewDoingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        task_id = intent.getIntExtra("task_id",-1)
        if (task_id==-1){
            Toast.makeText(this,"Error loading task", Toast.LENGTH_SHORT).show()
            finish()
        }

        getTask(this)

        binding.RequestViewDoingDone.setOnClickListener {
            showDialog(this)
        }

    }

    private fun showDialog(context: Context) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_done)

        val btncomfirm = dialog.findViewById(R.id.btnconfirm) as Button
        val btncancle = dialog.findViewById(R.id.btncancle) as Button
        btncomfirm.setOnClickListener {
            Donetask(context)
            val intent = Intent(this, MyListRequestActivity::class.java)
            finish()
            startActivity(intent)
        }
        btncancle.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }

    private fun Donetask(context: Context){
        val token = Cache.getToken(context).toString()
        val response = MyAPI.getAPI().postDoneRequest(token, DoneTaskBody(task_id.toString()))
        response.enqueue(object : Callback<DoneTaskResponse>{
            override fun onResponse(
                call: Call<DoneTaskResponse>,
                response: Response<DoneTaskResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()

                    if (data?.exitcode == 0) {
                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<DoneTaskResponse>, t: Throwable) {
                Toast.makeText(context, "Fail to connect to server", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun loadScreen(context: Context,task: Task){
        Log.d("test",task.title)
        binding.RequestViewDoingName.text =  task.title
        binding.RequestViewDoingDL.text =  task.deadline.dateformat()
        binding.RequestViewDoingCost.text =  task.offer.toString()
        binding.RequestViewDoingUser.text =  task.user_id
        binding.RequestViewContent.text =  task.description

        binding.RequestViewDoingChat.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)
            intent.putExtra("user_id", task.user_id)
            startActivity(intent)
        }
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
                    loadScreen(context,data!!.task)
                }
            }

            override fun onFailure(call: Call<TaskDetailResponse>, t: Throwable) {
                Toast.makeText(context, "Fail", Toast.LENGTH_LONG).show()
            }
        })
    }
}