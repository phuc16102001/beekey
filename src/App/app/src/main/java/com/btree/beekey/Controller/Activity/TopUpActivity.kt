package com.btree.beekey.Controller.Activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.btree.beekey.Model.TopUpBody
import com.btree.beekey.Model.TopUpResponse
import com.btree.beekey.Utils.Cache
import com.btree.beekey.Utils.MyAPI
import com.btree.beekey.databinding.ActivityTopUpBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTopUpBinding
    private val TAG = "TopUp"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTopUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnTopUp.setOnClickListener { clickTopUpBtn(this) }
    }

    private fun clickTopUpBtn(context: Context) {
        val amount = binding.edtAmount.text.toString().toIntOrNull()
        if (amount==null) {
            Toast.makeText(context,"Cannot leave field blank",Toast.LENGTH_SHORT).show()
            return
        }

        val token : String? = Cache.getToken(context)
        if (token==null){
            Intent(this,LoginActivity::class.java).also {
                startActivity(it)
            }
            finish()
        }

        val response = MyAPI.getAPI().postTopUp(token!!, TopUpBody(amount))
        response.enqueue(
            object : Callback<TopUpResponse> {
                override fun onResponse(
                    call: Call<TopUpResponse>,
                    response: Response<TopUpResponse>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()!!
                        val exitcode = data.exitcode
                        val message = data.message
                        Log.d(TAG,message)
                        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
                        if (exitcode==0) {
                            finish()
                        }
                    }
                }

                override fun onFailure(call: Call<TopUpResponse>, t: Throwable) {
                    Toast.makeText(context,"Cannot connect to server",Toast.LENGTH_SHORT).show()
                }
            }
        )

    }
}