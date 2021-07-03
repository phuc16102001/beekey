package com.btree.beekey.Controller.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.btree.beekey.R
import com.btree.beekey.Utils.Cache

class SettingsActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val personalSetting: TextView = findViewById(R.id.personal_setting)
        val changePassword: TextView = findViewById(R.id.password_setting)
        val logoutButton: TextView = findViewById(R.id.logout_button)

        personalSetting.setOnClickListener {
            Intent(this, ChangeInformationActivity::class.java).also {
                startActivity(it)
            }
        }

        changePassword.setOnClickListener {
            Intent(this, ChangePasswordActivity::class.java).also {
                startActivity(it)
            }
        }
        logoutButton.setOnClickListener{
            Cache.clear(this)
            Intent(this, LoginActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}
