package com.btree.beekey.Controller.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView
import com.btree.beekey.R
import com.google.android.material.card.MaterialCardView

class TasksAdapter (
    private val listTask: List<Task>
    ): RecyclerView.Adapter<TasksAdapter.ItemViewHolder>() {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardTaskView: MaterialCardView = view.findViewById(R.id.cardTaskView)
        val txtTitle: TextView = view.findViewById(R.id.txtTitle)
        val txtDeadline: TextView = view.findViewById(R.id.txtDeadline)
        val txtOffer: TextView = view.findViewById(R.id.txtOffer)
        val txtDescription: TextView = view.findViewById(R.id.txtDescription)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.item_list_task_request, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val task = listTask[position]
        holder.txtTitle.text = task.title
        holder.txtDeadline.text = ("Deadline: "+task.deadline.toString())
        holder.txtOffer.text = ("Offer: " + task.offer.toString())
        holder.txtDescription.text = task.description

        @ColorInt var bgColor = R.color.white
        if (task.status==Task.TASK_PENDING) {
            bgColor = R.color.white
        } else
        if (task.status==Task.TASK_DOING) {
            bgColor = R.color.beeyellow
        } else
        if (task.status==Task.TASK_DONE){
            bgColor = R.color.green
        }
        holder.cardTaskView.setBackgroundColor(bgColor)
    }

    override fun getItemCount(): Int {
        return listTask.size
    }
}