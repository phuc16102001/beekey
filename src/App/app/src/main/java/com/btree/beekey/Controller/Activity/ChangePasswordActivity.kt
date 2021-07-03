package com.btree.beekey.Controller.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.btree.beekey.Model.ChangePasswordPost
import com.btree.beekey.Model.ChangePasswordResponse
import com.btree.beekey.R
import com.btree.beekey.Utils.Cache
import com.btree.beekey.Utils.MyAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePasswordActivity : AppCompatActivity() {
    private var TAG = "ChangePasswordActivity"  //for debug

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        val OkButton = findViewById<Button>(R.id.OkButton)

        OkButton.setOnClickListener{
            ChangePassword()
        }
    }

    private fun ChangePassword() {
        val oldPassword = findViewById<EditText>(R.id.editTextPassword)
        val newPassword = findViewById<EditText>(R.id.editTextNewPassword)
        val ReEnterNewPassword = findViewById<EditText>(R.id.editReEnterPassword)

        val oldPasswordStr = oldPassword.text.toString()
        val newPasswordStr = newPassword.text.toString()
        val ReEnterNewPasswordStr = ReEnterNewPassword.text.toString()
        Log.d("xxxxx", oldPasswordStr)
        Log.d("xxxxx", newPasswordStr)

        while (oldPassword != newPassword)
            ChangePasswordActivity()

        val token = Cache.getToken(this).toString()
        val response = MyAPI.getAPI()
            .postChangePassword(token, ChangePasswordPost(oldPasswordStr, newPasswordStr))

        response.enqueue(object : Callback<ChangePasswordResponse> {
            override fun onResponse(
                call: Call<ChangePasswordResponse>,
                response: Response<ChangePasswordResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    Log.d("ChangePasswordStatus", data.toString())
                    if (data?.exitcode == 0) {
                        Intent(this@ChangePasswordActivity, ProfileActivity::class.java).also {
                            startActivity(it)
                        }
                    }
                    else if (data?.exitcode == 104){
                        Toast.makeText(this@ChangePasswordActivity, data.message, Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<ChangePasswordResponse>, t: Throwable) {
                Toast.makeText(this@ChangePasswordActivity, "Fail", Toast.LENGTH_LONG).show()
            }
        })
    }

}