package com.btree.beekey.Controller.Activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.btree.beekey.Model.PostFeedbackBody
import com.btree.beekey.Model.PostFeedbackResponse
import com.btree.beekey.R
import com.btree.beekey.Utils.Cache
import com.btree.beekey.Utils.MyAPI
import com.btree.beekey.databinding.ActivityFeedbackBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedbackActivity : AppCompatActivity() {
    private var TAG = "FeedbackActivity"  //for debug
    private var task_id = -1
    private lateinit var binding : ActivityFeedbackBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedbackBinding.inflate(layoutInflater)
        setContentView(binding.root)

        task_id = intent.getIntExtra("task_id",-1)
        if (task_id==-1){
            Toast.makeText(this,"Error loading task",Toast.LENGTH_SHORT).show()
            finish()
        }

        val sendButton: Button = findViewById(R.id.btSend)

        sendButton.setOnClickListener{
            clickSendBtn(this)
        }
    }


    private fun clickSendBtn(context: Context) {
        val title =binding.txtTitle.text.toString()
        val description = binding.txtFeedback.text.toString()
        Log.d("FeedbackStatus", description.toString())

        val token = Cache.getToken(this).toString()
        val response = MyAPI.getAPI().postFeedback(token, PostFeedbackBody(title,task_id,description))

        response.enqueue(object : Callback<PostFeedbackResponse> {
            override fun onResponse(
                call: Call<PostFeedbackResponse>,
                response: Response<PostFeedbackResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    Log.d("FeedbackStatus", data.toString())
                    if (data?.exitcode == 0) {
                        Toast.makeText(context,"Sending successfully", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    else if (data?.exitcode == 104){
                        Toast.makeText(context, data.message, Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<PostFeedbackResponse>, t: Throwable) {
                Toast.makeText(context, "Fail", Toast.LENGTH_LONG).show()
            }
        })
    }
}