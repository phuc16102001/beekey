package com.btree.beekey.Controller.Activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.Toast
import com.btree.beekey.Controller.Adapter.Task
import com.btree.beekey.Model.*
import com.btree.beekey.R
import com.btree.beekey.Utils.Cache
import com.btree.beekey.Utils.DateFormat.Companion.dateformat
import com.btree.beekey.Utils.MyAPI
import com.btree.beekey.databinding.ActivityRequestViewDoingBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RequestViewDoingActivity : AppCompatActivity() {
    private var task_id = -1
    private lateinit var binding: ActivityRequestViewDoingBinding
    private lateinit var displayTask : Task

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRequestViewDoingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        task_id = intent.getIntExtra("task_id",-1)
        if (task_id==-1){
            Toast.makeText(this,"Error loading task", Toast.LENGTH_SHORT).show()
            finish()
        }


        binding.btnChat.setOnClickListener {
            btnChatClick(this)
        }

        getTask(this)

        binding.btnConfirm.setOnClickListener {
            if (displayTask.status == Task.TASK_DOING) {
                showDialog(this)
            }
            else {
                Toast.makeText(this,"The task is already Done", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun btnChatClick(context: Context) {
        val intent = Intent(context, ChatActivity::class.java)
        intent.putExtra("user_id", displayTask.lancer_id)
        startActivity(intent)
    }

    private fun showDialog(context: Context) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_done)

        val btnConfirm : Button = dialog.findViewById(R.id.btnConfirm)
        val btnCancel : Button = dialog.findViewById(R.id.btnCancel)
        btnConfirm.setOnClickListener {
            doneTask(context)
        }
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun openFeedback(context: Context) {
        val intent = Intent(context, FeedbackActivity::class.java)
        intent.putExtra("lancer_id",displayTask.lancer_id)
        startActivity(intent)
        finish()
    }

    private fun doneTask(context: Context){
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
                        openFeedback(context)
                    }
                }
            }

            override fun onFailure(call: Call<DoneTaskResponse>, t: Throwable) {
                Toast.makeText(context, "Fail to connect to server", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun loadScreen(task: Task){
        binding.txtTitle.text =  task.title
        binding.txtDeadline.text =  task.deadline.dateformat()
        binding.txtOffer.text =  task.offer.toString()
        binding.txtLancer.text =  task.lancer_id
        binding.txtDescription.text =  task.description
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
                    displayTask = data!!.task
                    loadScreen(displayTask)
                }
            }

            override fun onFailure(call: Call<TaskDetailResponse>, t: Throwable) {
                Toast.makeText(context, "Fail", Toast.LENGTH_LONG).show()
            }
        })
    }
}