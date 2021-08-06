package com.btree.beekey.Controller.Activity

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.btree.beekey.Controller.Adapter.*
import com.btree.beekey.Model.ListTaskResponse
import com.btree.beekey.R
import com.btree.beekey.Utils.Cache
import com.btree.beekey.Utils.MyAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyListRequestActivity:AppCompatActivity() {
    private lateinit var listRequirement: List<Task>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_list_request)

        getListRequest(this)
    }

    private fun loadAdapter(){
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = TaskAdapter(listRequirement)
        recyclerView.setHasFixedSize(true)
    }

    private fun getListRequest(context: Context){
        val token = Cache.getToken(context).toString()
        val response = MyAPI.getAPI().getMyRequest(token)

        response.enqueue(object : Callback<ListTaskResponse>{
            override fun onResponse(
                call: Call<ListTaskResponse>,
                response: Response<ListTaskResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    //Log.d("getMyRequest", data.toString())
                    if (data?.exitcode == 0) {
                        listRequirement = data.tasks
                        loadAdapter()
                    }
                }
            }

            override fun onFailure(call: Call<ListTaskResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}
