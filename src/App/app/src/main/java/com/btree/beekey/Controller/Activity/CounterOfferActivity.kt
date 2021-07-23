package com.btree.beekey.Controller.Activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.btree.beekey.Model.ChangePasswordPost
import com.btree.beekey.Model.ChangePasswordResponse
import com.btree.beekey.Model.CounterOfferPost
import com.btree.beekey.Model.CounterOfferResponse
import com.btree.beekey.Utils.Cache
import com.btree.beekey.Utils.Hash256.Companion.sha256
import com.btree.beekey.Utils.MyAPI
import com.btree.beekey.databinding.ActivityChangePasswordBinding
import com.btree.beekey.databinding.ActivityCounterOfferBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CounterOfferActivity : AppCompatActivity() {
    private var TAG = "CounterOfferActivity"  //for debug
    private lateinit var binding:ActivityCounterOfferBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCounterOfferBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.SendButton.setOnClickListener{
            clickSendBtn(this)
        }
    }

    private fun clickSendBtn(context: Context) {
        val reason = binding.edtReason.text.toString()
        val offer = binding.edtOfferPrice.text.toString().toIntOrNull()
        if (offer==null) {
            Toast.makeText(context,"Cannot leave field blank",Toast.LENGTH_SHORT).show()
            return
        }

        val token = Cache.getToken(this).toString()
        val response = MyAPI.getAPI().postCounterOffer(token, CounterOfferPost(taskId,reason,offer))
        Log.d("CounterOfferStatus:", taskId.toString())

        response.enqueue(object : Callback<CounterOfferResponse> {
            override fun onResponse(
                call: Call<CounterOfferResponse>,
                response: Response<CounterOfferResponse>
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

            override fun onFailure(call: Call<CounterOfferResponse>, t: Throwable) {
                Toast.makeText(context, "Fail", Toast.LENGTH_LONG).show()
            }
        })
    }
}