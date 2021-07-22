package com.btree.beekey.Controller.Activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.btree.beekey.Model.ChangeInformationPost
import com.btree.beekey.Model.ChangeInformationResponse
import com.btree.beekey.R
import com.btree.beekey.Utils.Cache
import com.btree.beekey.Utils.MyAPI
import com.btree.beekey.databinding.ActivityChangeInformationBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangeInformationActivity : AppCompatActivity() {
    private var TAG = "ChangeInformationActivity"  //for debug
    private lateinit var binding : ActivityChangeInformationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val changeButton: Button = findViewById(R.id.btnChange)

        changeButton.setOnClickListener{
            clickChangeBtn(this)
        }
    }


    private fun clickChangeBtn(context: Context) {
        val name = binding.edtName.text.toString()
        val phone = binding.edtPhone.text.toString()

        val token = Cache.getToken(this).toString()
        val response = MyAPI.getAPI().postChangeInformation(
            token,
            ChangeInformationPost(name, phone)
        )

        response.enqueue(object : Callback<ChangeInformationResponse> {
            override fun onResponse(
                call: Call<ChangeInformationResponse>,
                response: Response<ChangeInformationResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    Log.d("ChangePasswordStatus", data.toString())
                    if (data?.exitcode == 0) {
                        Intent(context, ProfileActivity::class.java).also {
                            startActivity(it)
                        }
                    }
                    else if (data?.exitcode == 104){
                        Toast.makeText(context, data.message, Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<ChangeInformationResponse>, t: Throwable) {
                Toast.makeText(context, "Fail", Toast.LENGTH_LONG).show()
            }
        })
    }
}