package com.example.wealthparkassignment.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wealthparkassignment.R
import com.example.wealthparkassignment.data.model.GetCitiesResponse
import com.example.wealthparkassignment.databinding.CitiesRowItemBinding

class CitiesAdapter(val cities: List<GetCitiesResponse.GetCitiesResponseItem>, val onCityClicked : (GetCitiesResponse.GetCitiesResponseItem) ->
Unit) : RecyclerView.Adapter<CitiesAdapter
.CitiesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitiesViewHolder {
        return CitiesViewHolder(CitiesRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CitiesViewHolder, position: Int) {
        val item = cities[position]
        holder.viewBinding.city = item
        Glide.with(holder.itemView.context).load(item.image).centerCrop()
            .thumbnail(Glide.with(holder.itemView.context).load(R.drawable.loading_city)).into(holder.viewBinding.ivImage)
        holder.viewBinding.clCityRowParent.animation = AnimationUtils.loadAnimation(holder.itemView.context,R.anim.animation_one)
        holder.viewBinding.clCityRowParent.setOnClickListener {
            onCityClicked(item)
        }
    }

    override fun getItemCount(): Int =  cities.size

    class CitiesViewHolder(val viewBinding: CitiesRowItemBinding) : RecyclerView.ViewHolder(viewBinding.root)
}