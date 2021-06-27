package com.btree.beekey

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val userList = mutableListOf<Feedback>()
        userList.add(Feedback("Good","Description: Test test test","Thanh"))
        userList.add(Feedback("Good","Description: Test test test","Phuc"))
        userList.add(Feedback("Good","Description: Test test test","Khanh"))
        userList.add(Feedback("Good","Description: Test test test","Khoa"))
        userList.add(Feedback("Good","Description: Test test test","Khoa1"))
        userList.add(Feedback("Good","Description: Test test test","Khoa2"))
        userList.add(Feedback("Good","Description: Test test test","Khoa3"))
        Log.d("user",userList.size.toString());

        val recycler_view: RecyclerView = findViewById(R.id.recycler)
        recycler_view.adapter=FeedbackAdapter(userList)
    }
}