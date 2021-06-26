package com.btree.beekey

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.btree.beekey.api.LoginPost
import com.btree.beekey.api.LoginResponse
import kotlinx.coroutines.*
import retrofit2.*


import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

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
            Intent(this, ChangePasswordActivity::class.java).also {
                startActivity(it)
            }
        }

        signup.setOnClickListener {
            Intent(this, SignUpActivity::class.java).also {
                startActivity(it)
            }
        }

    }

    private fun CheckAccount(){
        val Username = findViewById<EditText>(R.id.LoginUsername)
        val Password = findViewById<EditText>(R.id.LoginPassword)

        val usernameStr = Username.text.toString()
        val passwordStr = Password.text.toString()

        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(API::class.java)

        val response = api.postlogin(LoginPost(usernameStr,passwordStr))

        response.enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful){
                    val data = response.body()
                    Log.d("LoginStatus",data.toString())
                    if (data?.exitcode == 0) {

//                        val cache = HashMap<Any,Any>()
//                        cache["token"] = data.token
//
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

