package com.greener.presentation.ui.home.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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
            holder.binding.ivItemProfileBackground.setImageResource(R.drawable.img_profile_background_circle_selected)
            holder.binding.tvItemProfilePlantName.setTextColor(
                ContextCompat.getColor(
                    holder.binding.tvItemProfilePlantName.context,
                    R.color.green300
                )
            )
        }
    }

    inner class ProfileViewHolder(val binding: ItemProfileBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ExampleModel) {
            binding.profile = item
        }
    }
}