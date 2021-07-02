package com.btree.beekey.Controller.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.btree.beekey.Model.SignUpPost
import com.btree.beekey.Model.SignUpResponse
import com.btree.beekey.R
import com.btree.beekey.Utils.Hash256.Companion.sha256
import com.btree.beekey.Utils.MyAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        val PhoneNum = findViewById<EditText>(R.id.SignUpPhoneNumer)
        val Homeadd = findViewById<EditText>(R.id.SignUpHomeAdd)
        val Gender = findViewById<Spinner>(R.id.SignUpGender)


        val usernameStr = Username.text.toString()
        val passwordStr = Password.text.toString().sha256()
        val PhoneNumStr = PhoneNum.text.toString()
        val HomeaddStr = Homeadd.text.toString()
        val GenderStr = Gender.selectedItem.toString()
        var GenderBool = true

        if (GenderStr == "Female") {
            GenderBool = false
        }
        Log.d("check pass",passwordStr)
        val response = MyAPI.getAPI()
            .postsignup(SignUpPost(usernameStr, passwordStr, PhoneNumStr, HomeaddStr, GenderBool))
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
            return false
        }
        else if (passwordStr!=reenterPasswordStr){
            Toast.makeText(this, "Re-enter Password wrong", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}