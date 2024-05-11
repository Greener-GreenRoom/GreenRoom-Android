package com.greener.presentation.ui.home.decoration.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.greener.domain.model.asset.PlantShapeInfo
import com.greener.presentation.databinding.ItemAssetDetailItemBinding

class DecorationPlantShapeAdapter(

): ListAdapter<PlantShapeInfo, DecorationPlantShapeAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemAssetDetailItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemAssetDetailItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        init {
            if (getItem(absoluteAdapterPosition).id == 0) {

            }
        }

        fun bind(item: PlantShapeInfo) {

        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<PlantShapeInfo>() {
            override fun areItemsTheSame(
                oldItem: PlantShapeInfo,
                newItem: PlantShapeInfo
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: PlantShapeInfo,
                newItem: PlantShapeInfo
            ): Boolean = oldItem == newItem
        }
    }
}
