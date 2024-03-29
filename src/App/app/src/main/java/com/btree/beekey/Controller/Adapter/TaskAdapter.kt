package com.btree.beekey.Controller.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView
import com.btree.beekey.R
import com.btree.beekey.Utils.DateFormat.Companion.dateformat

class TaskAdapter (private val listTask: List<Task>):
    RecyclerView.Adapter<TaskAdapter.ItemViewHolder>() {

    private var clickListener: ItemClickListener? = null

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener{
        val layoutTask: RelativeLayout = view.findViewById(R.id.layoutTask)
        val txtTitle: TextView = view.findViewById(R.id.txtTitle)
        val txtDeadline: TextView = view.findViewById(R.id.txtDeadline)
        val txtOffer: TextView = view.findViewById(R.id.txtOffer)
        val txtDescription: TextView = view.findViewById(R.id.txtDescription)

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
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.item_list_task_request, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    fun setClickListener(itemClickListener: ItemClickListener){
        this.clickListener = itemClickListener
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val task = listTask[position]
        holder.txtTitle.text = task.title
        holder.txtDeadline.text = ("Deadline: "+task.deadline.dateformat())
        holder.txtOffer.text = ("Offer: " + task.offer.toString())
        holder.txtDescription.text = task.description

        @ColorInt var bgColor = R.color.white
        when (task.status) {
            Task.TASK_PENDING -> {
                bgColor = R.color.white
            }
            Task.TASK_DOING -> {
                bgColor = R.color.bee_yellow_light
            }
            Task.TASK_DONE -> {
                bgColor = R.color.teal_200
            }
        }
        holder.layoutTask.setBackgroundResource(bgColor)
    }

    override fun getItemCount(): Int {
        return listTask.size
    }
}