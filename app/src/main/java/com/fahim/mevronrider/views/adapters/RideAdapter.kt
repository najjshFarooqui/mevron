package com.fahim.mevronrider.views.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fahim.mevronrider.R
import com.fahim.mevronrider.models.RideHistory
import kotlinx.android.synthetic.main.rides_list.view.*

class RideAdapter(var list: List<RideHistory>, val context: Context) :
    RecyclerView.Adapter<RideAdapter.RideViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RideViewHolder {
        return RideViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rides_list, parent, false))
    }

    fun notifyDataSetChanged(list: List<RideHistory>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: RideViewHolder, position: Int) {
        holder.bindTo(list[position])
    }

    inner class RideViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindTo(place: RideHistory) {

            itemView.tvDestination
            itemView.tvDestination.text = place.destination
            itemView.tvRideDistance.text = place.distance
            itemView.tvETA.text = place.eta
            itemView.tvRidePrice.text = place.earned


            itemView.setOnClickListener {
                val details = list.get(adapterPosition)
                val intent =
                    Intent(itemView.context, com.fahim.mevronrider.views.activity.RideDetailsActivity::class.java)

                intent.putExtra("destination", details.destination)
                intent.putExtra("distance", details.distance)
                intent.putExtra("earned", details.earned)
                intent.putExtra("eta", details.eta)

                itemView.context.startActivity(intent)
            }
        }
    }

}


