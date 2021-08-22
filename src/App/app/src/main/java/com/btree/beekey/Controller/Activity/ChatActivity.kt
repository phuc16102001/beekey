package com.btree.beekey.Controller.Activity

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.btree.beekey.Controller.Adapter.ChatAdapter
import com.btree.beekey.Controller.Adapter.Message
import com.btree.beekey.Model.BasicResponse
import com.btree.beekey.Model.FetchMessageBody
import com.btree.beekey.Model.FetchMessageResponse
import com.btree.beekey.Model.SendMessageBody
import com.btree.beekey.Utils.Cache
import com.btree.beekey.Utils.MyAPI
import com.btree.beekey.databinding.ActivityChatBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatActivity : AppCompatActivity() {
    private lateinit var binding:ActivityChatBinding
    private lateinit var receive_id:String
    private lateinit var listMessage:List<Message>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.reverseLayout = true
        binding.listMessage.layoutManager = linearLayoutManager

        receive_id = intent.getStringExtra("user_id").toString()
        fetchMessage(this)

        binding.btnSend.setOnClickListener { clickBtnSend(this) }
    }

    private fun clickBtnSend(context: Context) {
        val content = binding.edtMessage.text.toString()
        binding.edtMessage.text.clear()
        sendMessage(context,content)
    }

    private fun sendMessage(context: Context, content: String) {
        val token = Cache.getToken(context).toString()
        val response = MyAPI.getAPI().postSendMessage(token, SendMessageBody(receive_id,content))
        response.enqueue(object : Callback<BasicResponse> {
            override fun onResponse(
                call: Call<BasicResponse>,
                response: Response<BasicResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data!!.exitcode == 0) {
                        Toast.makeText(context,"Sent",Toast.LENGTH_SHORT).show()
                        fetchMessage(context)
                    } else {
                        Toast.makeText(context,data.message,Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                Toast.makeText(context, "Fail to connect to server", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun loadMessage(listMessage: List<Message>){
        binding.listMessage.adapter = ChatAdapter(listMessage.reversed(),receive_id)
    }

    private fun fetchMessage(context:Context){
        val token = Cache.getToken(context).toString()
        val response = MyAPI.getAPI().postFetchMessage(token, FetchMessageBody(receive_id))
        response.enqueue(object : Callback<FetchMessageResponse> {
            override fun onResponse(
                call: Call<FetchMessageResponse>,
                response: Response<FetchMessageResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data!!.exitcode == 0) {
                        listMessage = data.chats
                        loadMessage(listMessage)
                    }
                }
            }

            override fun onFailure(call: Call<FetchMessageResponse>, t: Throwable) {
                Toast.makeText(context, "Fail to connect to server", Toast.LENGTH_SHORT).show()
            }
        })
    }
}