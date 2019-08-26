package com.fahim.mevronrider

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fahim.mevronrider.models.Earnings
import kotlinx.android.synthetic.main.earning_list.view.*

class RideHistoryAdapter(var list: List<Earnings>, val context: Context) :
    RecyclerView.Adapter<RideHistoryAdapter.RideHistoryViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RideHistoryViewHolder {
        return RideHistoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.earning_list, parent, false))
    }

    fun notifyDataSetChanged(list: List<Earnings>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: RideHistoryViewHolder, position: Int) {
        holder.bindTo(list[position])
    }

    inner class RideHistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindTo(place: Earnings) {
            itemView.tvEarningDate.text = place.rideDate
            itemView.tvEarningPrice.text = place.price

        }
    }

}