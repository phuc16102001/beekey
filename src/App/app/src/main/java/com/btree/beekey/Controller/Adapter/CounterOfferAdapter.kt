package com.btree.beekey.Controller.Adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView
import com.btree.beekey.R
import com.btree.beekey.Utils.DateFormat.Companion.dateformat

class CounterOfferAdapter (private val listOffer: List<CounterOffer>):
    RecyclerView.Adapter<CounterOfferAdapter.ItemViewHolder>() {

    private var clickListener: ItemClickListener? = null

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val txtLancer: TextView = view.findViewById(R.id.txtLancer)
        val txtReason: TextView = view.findViewById(R.id.txtReason)
        val txtOffer: TextView = view.findViewById(R.id.txtOffer)

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            clickListener?.onClick(view!!, adapterPosition)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.item_offer, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    fun setClickListener(itemClickListener: ItemClickListener) {
        this.clickListener = itemClickListener
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val offer = listOffer[position]
        holder.txtLancer.text = offer.lancerId.toString()
        holder.txtReason.text = offer.reason
        holder.txtOffer.text = offer.offer.toString()
    }

    override fun getItemCount(): Int {
        return listOffer.size
    }
}