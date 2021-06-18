package com.btree.beekey

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView

class ProfileActivityActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_activity)

        val userList = mutableListOf<Feedback>()
        userList.add(Feedback("Good","Description: Test test test","Thanh"))
        userList.add(Feedback("Good","Description: Test test test","Phuc"))
        userList.add(Feedback("Good","Description: Test test test","Khanh"))
        userList.add(Feedback("Good","Description: Test test test","Khoa"))
        Log.d("user",userList.size.toString());

        val recycler_view: RecyclerView = findViewById(R.id.recycler)
        recycler_view.adapter=FeedbackAdapter(userList)
    }
}
