package com.btree.beekey.Controller.Activity

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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ChangeInformationActivity : AppCompatActivity() {
    private var TAG = "ChangeInformationActivity"  //for debug

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_information)

        val OkButton: Button = findViewById(R.id.OkButton)

        OkButton.setOnClickListener{
            ChangeInformation()
        }
    }


    private fun ChangeInformation() {
        val name = findViewById<EditText>(R.id.editTextName)
        val phone = findViewById<EditText>(R.id.editTextPhone)
        val address = findViewById<EditText>(R.id.editTextAddress)
        val genderMale = findViewById<RadioButton>(R.id.radioButtonMale)

        val nameStr = name.text.toString()
        val phoneStr = phone.text.toString()
        val addressStr = address.text.toString()

        val genderStr: Boolean = genderMale.isChecked

        val token = Cache.getToken(this).toString()
        val response = MyAPI.getAPI().postChangeInformation(
            token,
            ChangeInformationPost(nameStr, phoneStr, addressStr, genderStr)
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
                        Intent(this@ChangeInformationActivity, ProfileActivity::class.java).also {
                            startActivity(it)
                        }
                    }
                    else if (data?.exitcode == 104){
                        Toast.makeText(this@ChangeInformationActivity, data.message, Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<ChangeInformationResponse>, t: Throwable) {
                Toast.makeText(this@ChangeInformationActivity, "Fail", Toast.LENGTH_LONG).show()
            }
        })
    }
}