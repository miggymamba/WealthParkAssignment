package com.example.wealthparkassignment.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wealthparkassignment.R
import com.example.wealthparkassignment.data.model.GetCitiesResponse
import com.example.wealthparkassignment.data.model.GetFoodsResponse
import com.example.wealthparkassignment.databinding.FoodsRowItemBinding

class FoodsAdapter(val foods: List<GetFoodsResponse.GetFoodsResponseItem>, val onFoodClicked : (GetFoodsResponse.GetFoodsResponseItem) ->
Unit) : RecyclerView.Adapter<FoodsAdapter.FoodsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodsViewHolder {
        return FoodsViewHolder(FoodsRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: FoodsViewHolder, position: Int) {
        val item = foods[position]
        holder.viewBinding.food = item
        Glide.with(holder.itemView.context).load(item.image).centerCrop()
            .thumbnail(Glide.with(holder.itemView.context).load(R.drawable.loading_food)).into(holder.viewBinding.ivImage)
        holder.viewBinding.clFoodRowParent.animation = AnimationUtils.loadAnimation(holder.itemView.context,R.anim.animation_one)
        holder.viewBinding.clFoodRowParent.setOnClickListener {
            onFoodClicked(item)
        }
    }

    override fun getItemCount(): Int = foods.size

    class FoodsViewHolder(val viewBinding: FoodsRowItemBinding) : RecyclerView.ViewHolder(viewBinding.root)
}