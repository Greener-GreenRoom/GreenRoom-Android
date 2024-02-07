package com.greener.presentation.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.greener.domain.model.ExampleModel
import com.greener.presentation.R
import com.greener.presentation.databinding.ItemProfileBinding

class ProfileRVAdapter(
    private val profiles: List<ExampleModel>,
    private var currentProfile: ExampleModel?,
    val onItemClick: (Int) -> Unit,
    val unSelect: (Int) -> Unit
) : RecyclerView.Adapter<ProfileRVAdapter.ProfileViewHolder>() {

    private var currentPosition = profiles.indexOf(currentProfile)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val binding: ItemProfileBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_profile,
            parent,
            false
        )
        return ProfileViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return profiles.size
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onItemClick(position)
            unSelect(currentPosition)
            currentPosition = holder.bindingAdapterPosition
        }
        holder.bind(profiles[position])
        if (holder.binding.profile == currentProfile) {
            holder.binding.imgProfile.strokeWidth = 5f
        }
    }

    inner class ProfileViewHolder(val binding: ItemProfileBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ExampleModel) {
            binding.profile = item
        }
    }
}