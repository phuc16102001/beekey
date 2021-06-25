package com.btree.beekey

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.btree.beekey.api.LoginPost
import com.btree.beekey.api.LoginResponse
import com.btree.beekey.api.SignUpPost
import com.btree.beekey.api.SignUpResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val button : Button = findViewById(R.id.button_signup)

        button.setOnClickListener {
            Signupaccount()
        }
    }

    private fun Signupaccount(){
        val Username = findViewById<EditText>(R.id.SignUpUserName)
        val Password = findViewById<EditText>(R.id.SignUpPassword)
        val reenterPassword = findViewById<EditText>(R.id.SignUpReEnterPass)
        val PhoneNum = findViewById<EditText>(R.id.SignUpPhoneNumer)
        val Homeadd = findViewById<EditText>(R.id.SignUpHomeAdd)

        val usernameStr = Username.text.toString()
        val passwordStr = Password.text.toString()
        val reenterPasswordStr = reenterPassword.text.toString()
        val PhoneNumStr = PhoneNum.text.toString()
        val HomeaddStr = Homeadd.text.toString()


        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(API::class.java)

        val response = api.postsignup(SignUpPost(usernameStr,passwordStr,PhoneNumStr,HomeaddStr,true))
        if (checkFill()) {
            response.enqueue(object : Callback<SignUpResponse> {
                override fun onResponse(
                    call: Call<SignUpResponse>,
                    response: Response<SignUpResponse>
                ) {
                    if (response.isSuccessful){
                        val data = response.body()
                        Log.d("LoginStatus",data.toString())
                        if (data?.exitcode == 0) {
                            Toast.makeText(this@SignUpActivity, data.message, Toast.LENGTH_LONG).show()
                            Intent(this@SignUpActivity, LoginActivity::class.java).also {
                                startActivity(it)
                            }
                            onDestroy()
                        }
                        else if (data?.exitcode == 1){
                            Toast.makeText(this@SignUpActivity, data.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }

                override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                    Toast.makeText(this@SignUpActivity, "Click Fail", Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    private fun checkFill():Boolean{
        val Username = findViewById<EditText>(R.id.SignUpUserName)
        val Password = findViewById<EditText>(R.id.SignUpPassword)
        val reenterPassword = findViewById<EditText>(R.id.SignUpReEnterPass)
        val PhoneNum = findViewById<EditText>(R.id.SignUpPhoneNumer)
        val Homeadd = findViewById<EditText>(R.id.SignUpHomeAdd)

        val usernameStr = Username.text.toString()
        val passwordStr = Password.text.toString()
        val reenterPasswordStr = reenterPassword.text.toString()
        val PhoneNumStr = PhoneNum.text.toString()
        val HomeaddStr = Homeadd.text.toString()
        if (usernameStr == "" || passwordStr == "" || reenterPasswordStr == "" || PhoneNumStr == "" || HomeaddStr == ""){
            Toast.makeText(this, "Must fill all plain", Toast.LENGTH_SHORT).show()
            return false;
        }
        else if (passwordStr!=reenterPasswordStr){
            Toast.makeText(this, "Re-enter Password wrong", Toast.LENGTH_SHORT).show()
            return false;
        }
        return true;
    }
}