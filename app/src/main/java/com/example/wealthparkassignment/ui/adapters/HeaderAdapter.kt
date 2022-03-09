package com.example.wealthparkassignment.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.wealthparkassignment.R
import com.example.wealthparkassignment.databinding.HeaderRowItemBinding

class HeaderAdapter(val headerTitle : String) : RecyclerView.Adapter<HeaderAdapter.HeaderViewHolder>() {

    class HeaderViewHolder(val viewBinding: HeaderRowItemBinding) : RecyclerView.ViewHolder(viewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        return HeaderViewHolder(HeaderRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {
        holder.viewBinding.text = headerTitle
        holder.viewBinding.clHeaderRowParent.animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.animation_one)
    }

    override fun getItemCount(): Int = 1
}