package com.greener.presentation.ui.home.main

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.greener.domain.model.ExampleModel
import com.greener.domain.model.greenroom.GreenRoomTotalInfo
import com.greener.presentation.R
import com.greener.presentation.databinding.ItemProfileBinding

class ProfileRVAdapter(
    private val profiles: List<GreenRoomTotalInfo>,
    private var currentProfile: GreenRoomTotalInfo?,
    val onItemClick: (Int) -> Unit,
) : RecyclerView.Adapter<ProfileRVAdapter.ProfileViewHolder>() {
    init {
        Log.d("확인","RVAdapter: $profiles")
    }

    private var currentPosition = profiles.indexOf(currentProfile)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val binding: ItemProfileBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_profile,
            parent,
            false,
        )
        return ProfileViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return profiles.size
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        val currentItem = profiles[position]
        holder.bind(currentItem)

        // 선택된 아이템에만 테두리 추가
        if (position == currentPosition) {
            holder.binding.ivItemProfileBackground.setImageResource(R.drawable.img_profile_background_circle_selected)
            holder.binding.tvItemProfilePlantName.setTextColor(
                ContextCompat.getColor(
                    holder.binding.tvItemProfilePlantName.context,
                    R.color.green300,
                )
            )
        } else {
            holder.binding.ivItemProfileBackground.setImageResource(R.drawable.img_profile_background_circle_non_selected)
            holder.binding.tvItemProfilePlantName.setTextColor(
                ContextCompat.getColor(
                    holder.binding.tvItemProfilePlantName.context,
                    R.color.gray700,
                ),
            )
        }
        holder.itemView.setOnClickListener {
            onItemClick(position)

            val previouslySelectedItemPosition = currentPosition
            currentPosition = holder.bindingAdapterPosition

            notifyItemChanged(previouslySelectedItemPosition)
            notifyItemChanged(currentPosition)
        }
    }

    inner class ProfileViewHolder(val binding: ItemProfileBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GreenRoomTotalInfo) {
            binding.profile = item
        }
    }
}
