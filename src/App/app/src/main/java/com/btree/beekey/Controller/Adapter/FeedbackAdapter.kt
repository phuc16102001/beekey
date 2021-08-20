package com.btree.beekey.Controller.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.btree.beekey.R

class FeedbackAdapter (private val listFeedback:List<Feedback>) :
    RecyclerView.Adapter<FeedbackAdapter.ItemViewHolder>(){
    class ItemViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val txtTitle:TextView = view.findViewById(R.id.txtTitle)
        val txtDescription:TextView = view.findViewById(R.id.txtDescription)
        val txtUsername:TextView = view.findViewById(R.id.txtUsername)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.feedback_item,parent,false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val feedback = listFeedback[position]
        holder.txtTitle.text = feedback.title
        holder.txtDescription.text = feedback.description
        holder.txtUsername.text = feedback.userID
    }

    override fun getItemCount(): Int {
        return listFeedback.size
    }
}