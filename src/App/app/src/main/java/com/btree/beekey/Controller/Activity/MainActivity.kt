package com.btree.beekey.Controller.Activity
import ProfileFragment
import SettingFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.btree.beekey.Controller.Fragment.PostTaskFragment
import com.btree.beekey.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val settingFragment=SettingFragment()
        val profileFragment=ProfileFragment()
        val postFragment= PostTaskFragment()

        setCurrentFragment(profileFragment)
        val bottomBar: BottomNavigationView = findViewById(R.id.bottom_bar)
        bottomBar.setOnItemSelectedListener {
            when(it.itemId){
                R.id.menu_setting->setCurrentFragment(settingFragment)
                R.id.menu_profile->setCurrentFragment(profileFragment)
                R.id.menu_post->setCurrentFragment(postFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }
}