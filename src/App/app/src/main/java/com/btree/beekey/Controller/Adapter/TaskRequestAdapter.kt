package com.btree.beekey.Controller.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.btree.beekey.R

class TaskRequestAdapter (
    private val ListTask: List<TaskRequest>
    ): RecyclerView.Adapter<TaskRequestAdapter.ItemViewHolder>() {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val clientname_taskrequest: TextView = view.findViewById(R.id.clientname_taskrequest)
        val category_taskrequest: TextView = view.findViewById(R.id.category_taskrequest)
        val deadline_taskrequest: TextView = view.findViewById(R.id.deadline_taskrequest)
        val offer_taskrequest: TextView = view.findViewById(R.id.offer_taskrequest)
        val context_taskrequest: TextView = view.findViewById(R.id.context_taskrequest)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_task_request, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val ListTask = ListTask[position]
        Log.d("Binding",ListTask.toString())
        holder.clientname_taskrequest.text=ListTask.client_name
        holder.category_taskrequest.text=ListTask.category
        holder.deadline_taskrequest.text=ListTask.deadline
        holder.offer_taskrequest.text=("Offer: " + ListTask.offer.toString())
        holder.context_taskrequest.text=ListTask.context
    }

    override fun getItemCount(): Int {
        return ListTask.size
    }
}