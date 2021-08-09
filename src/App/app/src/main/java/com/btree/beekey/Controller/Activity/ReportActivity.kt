package com.btree.beekey.Controller.Activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.btree.beekey.Model.PostReportResponse
import com.btree.beekey.Model.PostReportBody
import com.btree.beekey.R
import com.btree.beekey.Utils.Cache
import com.btree.beekey.Utils.MyAPI
import com.btree.beekey.databinding.ActivityReportBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReportActivity : AppCompatActivity() {
    private var TAG = "ReportActivity"  //for debug
    private lateinit var binding : ActivityReportBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sendButton: Button = findViewById(R.id.sendButton)

        sendButton.setOnClickListener{
            clickSendBtn(this)
        }
    }


    private fun clickSendBtn(context: Context) {
        val content = binding.txtContent.text.toString()
        Log.d("ReportStatus", content.toString())

        val token = Cache.getToken(this).toString()
        val response = MyAPI.getAPI().postReport(token, PostReportBody(content))

        response.enqueue(object : Callback<PostReportResponse> {
            override fun onResponse(
                call: Call<PostReportResponse>,
                response: Response<PostReportResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    Log.d("ReportStatus", data.toString())
                    if (data?.exitcode == 0) {
                        Toast.makeText(context,"Posted report successfully",Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    else if (data?.exitcode == 104){
                        Toast.makeText(context, data.message, Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<PostReportResponse>, t: Throwable) {
                Toast.makeText(context, "Fail", Toast.LENGTH_LONG).show()
            }
        })
    }
}