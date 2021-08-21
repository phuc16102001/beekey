package com.btree.beekey.Controller.Activity

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.btree.beekey.Controller.Adapter.CounterOffer
import com.btree.beekey.Controller.Adapter.CounterOfferAdapter
import com.btree.beekey.Controller.Adapter.ItemClickListener
import com.btree.beekey.Model.AcceptOfferBody
import com.btree.beekey.Model.BasicResponse
import com.btree.beekey.Model.GetOfferBody
import com.btree.beekey.Model.ListCounterOfferResponse
import com.btree.beekey.R
import com.btree.beekey.Utils.Cache
import com.btree.beekey.Utils.MyAPI
import com.btree.beekey.databinding.ActivityViewCounterOfferListBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ViewCounterOfferListActivity : AppCompatActivity() {
    private var task_id = -1
    private lateinit var listOffer : List<CounterOffer>
    private lateinit var binding: ActivityViewCounterOfferListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewCounterOfferListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        task_id = intent.getIntExtra("task_id",-1)

        if (task_id==-1){
            Toast.makeText(this,"Error loading offer", Toast.LENGTH_SHORT).show()
            finish()
        }

        getOfferList(this)
    }

    private fun showDialogAccept(context: Context, offer: CounterOffer) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_accept_offer)

        val btnConfirm : Button = dialog.findViewById(R.id.btnConfirm)
        val btnCancel : Button = dialog.findViewById(R.id.btnCancel)
        btnConfirm.setOnClickListener {
            sendAccept(context, offer)
        }
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun sendAccept(context: Context, offer: CounterOffer) {
        val token = Cache.getToken(context).toString()
        val response = MyAPI.getAPI().postAcceptOffer(token, AcceptOfferBody(offer.taskId,offer.lancerId))

        response.enqueue(object : Callback<BasicResponse> {
            override fun onResponse(
                call: Call<BasicResponse>,
                response: Response<BasicResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data!!.exitcode == 0) {
                        Toast.makeText(context,data.message,Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(context,data.message,Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                Toast.makeText(context, "Fail", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun loadOfferList(context: Context, offers :List<CounterOffer>) {
        val offerAdapter = CounterOfferAdapter(offers)
        offerAdapter.setClickListener(object : ItemClickListener {
            override fun onClick(view: View, position: Int) {
                showDialogAccept(context, listOffer[position])
            }
        })
        binding.offerList.adapter = offerAdapter
    }

    private fun getOfferList(context: Context){
        val token = Cache.getToken(context).toString()
        val response = MyAPI.getAPI().postGetOfferList(token, GetOfferBody(task_id))

        response.enqueue(object : Callback<ListCounterOfferResponse> {
            override fun onResponse(
                call: Call<ListCounterOfferResponse>,
                response: Response<ListCounterOfferResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data?.exitcode!= 0) {
                        finish()
                    }
                    listOffer = data!!.offers
                    loadOfferList(context, listOffer)
                }
            }

            override fun onFailure(call: Call<ListCounterOfferResponse>, t: Throwable) {
                Toast.makeText(context, "Fail", Toast.LENGTH_LONG).show()
            }
        })
    }
}