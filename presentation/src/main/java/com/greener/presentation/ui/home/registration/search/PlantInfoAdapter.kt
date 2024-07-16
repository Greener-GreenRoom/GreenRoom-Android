package com.greener.presentation.ui.home.registration.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.greener.domain.model.plant_register.PlantInformationData
import com.greener.presentation.databinding.ItemSearchPlantBinding

class PlantInfoAdapter(
    val onClickPlantInfo: (Long) -> Unit,
) : ListAdapter<PlantInformationData, PlantInfoAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemSearchPlantBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(
        private val binding: ItemSearchPlantBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PlantInformationData) {
            binding.data = item

            binding.root.setOnClickListener {
                onClickPlantInfo(item.plantId)
            }
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<PlantInformationData>() {
            override fun areItemsTheSame(
                oldItem: PlantInformationData,
                newItem: PlantInformationData,
            ): Boolean =
                oldItem.plantId == newItem.plantId

            override fun areContentsTheSame(
                oldItem: PlantInformationData,
                newItem: PlantInformationData,
            ): Boolean =
                oldItem == newItem
        }
    }
}
