package com.btree.beekey.Controller.Adapter

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.btree.beekey.R
import com.btree.beekey.Utils.DateFormat.Companion.dateformat


class TasksAdapter (
    private val ListTask: List<Tasks>
    ): RecyclerView.Adapter<TasksAdapter.ItemViewHolder>() {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val clientname_Tasks: TextView = view.findViewById(R.id.clientname_taskrequest)
        val complete_icon: ImageView = view.findViewById(R.id.complete_icon)
        val deadline_Tasks: TextView = view.findViewById(R.id.deadline_taskrequest)
        val offer_Tasks: TextView = view.findViewById(R.id.offer_taskrequest)
        val context_Tasks: TextView = view.findViewById(R.id.context_taskrequest)
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
        holder.clientname_Tasks.text=ListTask.title
        holder.deadline_Tasks.text=ListTask.deadline.dateformat()
        holder.offer_Tasks.text=("Offer " + ListTask.offer.toString()+"$")
        holder.context_Tasks.text=ListTask.description
        if (ListTask.status==0 || ListTask.status==1) {
            holder.complete_icon.setImageDrawable(null)
        }
    }

    override fun getItemCount(): Int {
        return ListTask.size
    }
}