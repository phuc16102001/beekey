package com.btree.beekey.Controller.Activity


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.btree.beekey.Model.LoginPost
import com.btree.beekey.Model.LoginResponse
import com.btree.beekey.R
import com.btree.beekey.Utils.Cache
import com.btree.beekey.Utils.Hash256.Companion.sha256
import com.btree.beekey.Utils.MyAPI
import kotlinx.coroutines.*
import retrofit2.*

class LoginActivity : AppCompatActivity() {

    private var TAG = "loginactivity"  //for debug

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton: Button = findViewById(R.id.LoginButton)
        val forget: TextView = findViewById(R.id.LoginForgetPassword)
        val signup: TextView = findViewById(R.id.Loginsignup)

        loginButton.setOnClickListener{
            CheckAccount()
        }

        forget.setOnClickListener {
            Intent(this, ForgetPasswordActivity::class.java).also {
                startActivity(it)
            }
        }

        signup.setOnClickListener {
            Intent(this, SignUpActivity::class.java).also {
                startActivity(it)
            }
        }

    }

    private fun CheckAccount() {
        val Username = findViewById<EditText>(R.id.LoginUsername)
        val Password = findViewById<EditText>(R.id.LoginPassword)

        val usernameStr = Username.text.toString()
        val passwordStr = Password.text.toString().sha256()
        Log.d("xxxxx",passwordStr)

        val response = MyAPI.getAPI().postLogin(LoginPost(usernameStr, passwordStr))

        response.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    Log.d("LoginStatus", data.toString())
                    if (data?.exitcode == 0) {

                        Log.d(
                            "Token saved",
                            Cache.saveToken(this@LoginActivity, data.token).toString()
                        )

                        Intent(this@LoginActivity, ProfileActivity::class.java).also {
                            startActivity(it)
                        }
                    }
                    else if (data?.exitcode == 104){
                        Toast.makeText(this@LoginActivity, data.message, Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Click Fail", Toast.LENGTH_LONG).show()
            }
        })
    }

}

