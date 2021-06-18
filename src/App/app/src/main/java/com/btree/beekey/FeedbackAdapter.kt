package com.btree.beekey

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FeedbackAdapter (private val listFeedback:List<Feedback>) :
    RecyclerView.Adapter<FeedbackAdapter.ItemViewHolder>(){
    class ItemViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val textGeneral:TextView=view.findViewById(R.id.general)
        val textBrief:TextView=view.findViewById(R.id.brief)
        val textName:TextView=view.findViewById(R.id.user_feedback_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.feedback_item,parent,false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val feedback = listFeedback[position]
        Log.d("Binding",feedback.toString())
        holder.textGeneral.text=feedback.general
        holder.textBrief.text=feedback.description
        holder.textName.text=feedback.name
    }

    override fun getItemCount(): Int {
        return listFeedback.size
    }
}