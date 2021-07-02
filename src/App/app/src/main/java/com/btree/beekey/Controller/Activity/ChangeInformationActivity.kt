package com.btree.beekey.Controller.Activity

import android.content.Intent
import android.net.sip.SipSession
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.btree.beekey.Model.ChangeInformationPost
import com.btree.beekey.Model.ChangeInformationResponse
import com.btree.beekey.Model.ChangeInformationHeader
import com.btree.beekey.R
import com.btree.beekey.Utils.Hash256.Companion.sha256
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

        val genderStr : Boolean = genderMale.isChecked


        val response = MyAPI.getAPI().postchangeinformation(ChangeInformationHeader("https://web.postman.co/workspace/My-Workspace~4bec7427-7b3c-45f2-8298-7f55532f1651/request/16409532-bfba8562-be30-40f5-86f6-1d1f5d966ebc"),ChangeInformationPost(nameStr, phoneStr, addressStr,genderStr))

        response.enqueue(object : Callback<ChangeInformationResponse> {
            override fun onResponse(call: Call<ChangeInformationResponse>, response: Response<ChangeInformationResponse>) {
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