package com.btree.beekey.Controller.Activity


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.btree.beekey.Model.GetInformationResponse
import com.btree.beekey.Model.LoginBody
import com.btree.beekey.Model.LoginResponse
import com.btree.beekey.Utils.Cache
import com.btree.beekey.Utils.Hash256.Companion.sha256
import com.btree.beekey.Utils.MyAPI
import com.btree.beekey.databinding.ActivityLoginBinding
import retrofit2.*

class LoginActivity : AppCompatActivity() {

    private var TAG = "loginactivity"  //for debug
    private lateinit var binding:ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        autoLogin(this)

        binding.btnLogin.setOnClickListener {
            checkAccount(this)
        }

        binding.txtForgetPassword.setOnClickListener {
            Intent(this, ForgetPasswordActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.txtSignup.setOnClickListener {
            Intent(this, SignUpActivity::class.java).also {
                startActivity(it)
            }
        }

    }

    private fun autoLogin(context: Context) {
        val token = Cache.getToken(context).toString()
        val response = MyAPI.getAPI().getInformation(token)

        response.enqueue(object : Callback<GetInformationResponse> {
            override fun onResponse(
                call: Call<GetInformationResponse>,
                response: Response<GetInformationResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    Log.d("AutoLoginStatus", data.toString())
                    if (data?.exitcode == 0) {
                        Intent(context, MainActivity::class.java).also {
                            startActivity(it)
                            Toast.makeText(context, "AutoLogin", Toast.LENGTH_LONG).show()
                            finish()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<GetInformationResponse>, t: Throwable) {
                Toast.makeText(context, "Fail connection to server", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun checkAccount(context: Context) {
        val usernameStr = binding.edtUsername.text.toString()
        val passwordStr = binding.edtPassword.text.toString().sha256()
        Log.d("CheckPasswordHash", passwordStr)

        val response = MyAPI.getAPI().postLogin(LoginBody(usernameStr, passwordStr))

        response.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    Log.d("LoginStatus", data.toString())
                    if (data?.exitcode == 0) {
                        Log.d("Token saved", Cache.saveToken(context, data.token).toString())
                        Intent(context, MainActivity::class.java).also {
                            startActivity(it)
                            finish()
                        }

                    } else if (data?.exitcode == 104) {
                        Toast.makeText(context, data.message, Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(context, "Fail connection to server", Toast.LENGTH_LONG).show()
            }
        })
    }
}

