package com.btree.beekey.Controller.Activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.btree.beekey.Model.SignUpBody
import com.btree.beekey.Model.SignUpResponse
import com.btree.beekey.Utils.Hash256.Companion.sha256
import com.btree.beekey.Utils.MyAPI
import com.btree.beekey.databinding.ActivitySignUpBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignup.setOnClickListener {
            SignUp(this)
        }
    }

    private fun SignUp(context: Context){
        val username = binding.edtUsername.text.toString()
        val password = binding.edtPassword.text.toString()
        val rePassword = binding.edtPassword.text.toString()
        val phone = binding.edtPhone.text.toString()
        val email = binding.edtEmail.text.toString()

        val response = MyAPI.getAPI().postSignup(SignUpBody(username, password.sha256(), phone, email))
        if (checkFill(username, password, rePassword, phone, email)) {
            response.enqueue(object : Callback<SignUpResponse> {
                override fun onResponse(
                    call: Call<SignUpResponse>,
                    response: Response<SignUpResponse>
                ) {
                    if (response.isSuccessful){
                        val data = response.body()
                        Log.d("LoginStatus",data.toString())
                        if (data?.exitcode == 0) {
                            Toast.makeText(context, data.message, Toast.LENGTH_LONG).show()
                            finish()
                        }
                        else if (data?.exitcode == 1){
                            Toast.makeText(context, data.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }

                override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                    Toast.makeText(context, "Click Fail", Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    private fun checkFill(username:String, password:String, rePassword: String, phone:String, email:String):Boolean{
        if (username == "" || password == "" || rePassword == "" || phone == "" || email == ""){
            Toast.makeText(this, "Must fill all plain", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password!=rePassword){
            Toast.makeText(this, "Re-enter Password wrong", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}