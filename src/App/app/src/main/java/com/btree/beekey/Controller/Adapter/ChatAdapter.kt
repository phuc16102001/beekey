package com.btree.beekey.Controller.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.btree.beekey.R
import com.google.android.material.card.MaterialCardView
import java.lang.Boolean.FALSE

class ChatAdapter (private val listMessage: List<Message>, private val receive_id: String):
    RecyclerView.Adapter<ChatAdapter.ItemViewHolder>() {

    private var clickListener: ItemClickListener? = null

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener{
        val cardMessage: MaterialCardView = view.findViewById(R.id.cardMessage)
        val txtContent: TextView = view.findViewById(R.id.txtContent)

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            clickListener?.onClick(view!!,adapterPosition)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    fun setClickListener(itemClickListener: ItemClickListener){
        this.clickListener = itemClickListener
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val message = listMessage[position]
        holder.txtContent.text = message.content

        if (message.send_id==receive_id) {
            val params : RelativeLayout.LayoutParams = holder.cardMessage.layoutParams as (RelativeLayout.LayoutParams)
            params.addRule(RelativeLayout.ALIGN_PARENT_END,0)
            holder.cardMessage.setBackgroundResource(R.color.white)
            holder.cardMessage.layoutParams = params
        }
    }

    override fun getItemCount(): Int {
        return listMessage.size
    }
}