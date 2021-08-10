package com.btree.beekey.Controller.Activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.btree.beekey.Controller.Adapter.*
import com.btree.beekey.Model.ListTaskResponse
import com.btree.beekey.R
import com.btree.beekey.Utils.Cache
import com.btree.beekey.Utils.MyAPI
import com.btree.beekey.databinding.ActivityLoginBinding
import com.btree.beekey.databinding.ActivityMyListRequestBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyListRequestActivity:AppCompatActivity() {
    private var listRequirement: List<Task>? = null
    private lateinit var binding: ActivityMyListRequestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyListRequestBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_my_list_request)

        getListRequest(this)

    }

    private fun loadAdapter(){
        if (listRequirement == null) {
            return
        }
        Log.d("getMyRequest", listRequirement.toString())
        val taskAdapter = TaskAdapter(listRequirement!!)
        taskAdapter.setClickListener(object : ItemClickListener {
            override fun onClick(view: View, position: Int) {
                Log.d("TAG","CLICK")
                if (listRequirement!![position].status==Task.TASK_PENDING){
                    val intent = Intent(this@MyListRequestActivity, RequestViewPendingActivity::class.java)
                    intent.putExtra("task_id", listRequirement!![position].task_id)
                    finish()
                    startActivity(intent)
                }
                if (listRequirement!![position].status==Task.TASK_DOING){
                    val intent = Intent(this@MyListRequestActivity, RequestViewDoingActivity::class.java)
                    intent.putExtra("task_id", listRequirement!![position].task_id)
                    startActivity(intent)
                }



            }
        })
        val recyclerView = findViewById<RecyclerView>(R.id.listRequestRecyclerView)
        recyclerView.adapter = taskAdapter
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
                Toast.makeText(context, "Fail", Toast.LENGTH_LONG).show()
            }
        })
    }
}
