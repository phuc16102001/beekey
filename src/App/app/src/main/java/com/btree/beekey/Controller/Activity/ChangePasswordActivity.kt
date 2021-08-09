package com.btree.beekey.Controller.Activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.btree.beekey.Model.ChangePasswordBody
import com.btree.beekey.Model.ChangePasswordResponse
import com.btree.beekey.Utils.Cache
import com.btree.beekey.Utils.Hash256.Companion.sha256
import com.btree.beekey.Utils.MyAPI
import com.btree.beekey.databinding.ActivityChangePasswordBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePasswordActivity : AppCompatActivity() {
    private var TAG = "ChangePasswordActivity"  //for debug
    private lateinit var binding:ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnChange.setOnClickListener{
            clickChangeBtn(this)
        }
    }

    private fun clickChangeBtn(context: Context) {
        val password = binding.edtPassword.text.toString().sha256()
        val newPassword = binding.edtNewPassword.text.toString().sha256()
        val rePassword = binding.edtRePassword.text.toString().sha256()

        if (newPassword != rePassword){
            Toast.makeText(context,"Re-enter password does not match",Toast.LENGTH_SHORT).show()
            return
        }

        val token = Cache.getToken(this).toString()
        val response = MyAPI.getAPI().postChangePassword(token, ChangePasswordBody(password, newPassword))

        response.enqueue(object : Callback<ChangePasswordResponse> {
            override fun onResponse(
                call: Call<ChangePasswordResponse>,
                response: Response<ChangePasswordResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    Log.d("ChangePasswordStatus", data.toString())
                    if (data?.exitcode == 0) {
                        finish()
                    }
                    else if (data?.exitcode == 104){
                        Toast.makeText(context, data.message, Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<ChangePasswordResponse>, t: Throwable) {
                Toast.makeText(context, "Fail", Toast.LENGTH_LONG).show()
            }
        })
    }
}