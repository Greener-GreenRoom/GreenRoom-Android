package com.greener.presentation.ui.home.registration.plant_shape

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.greener.domain.model.asset.PlantShapeInfo
import com.greener.domain.model.asset.PlantShapeType
import com.greener.presentation.R
import com.greener.presentation.databinding.ItemAssetDetailItemBinding

class RegistrationShapeAdapter(
    private val onClickPlantShape: (PlantShapeInfo, PlantShapeType) -> Unit,
) : ListAdapter<PlantShapeInfo, RegistrationShapeAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemAssetDetailItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    @SuppressLint("notifyDataSetChanged")
    inner class ViewHolder(
        private val binding: ItemAssetDetailItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PlantShapeInfo) {
            Glide.with(binding.root)
                .load(item.drawableID)
                .into(binding.ivItemAssetDetail)

            if (item.isChecked) {
                binding.root.setBackgroundResource(R.drawable.shape_asset_on)
            } else {
                binding.root.setBackgroundResource(R.drawable.shape_asset_off)
            }

            binding.root.setOnClickListener {
                onClickPlantShape(item, item.plantShapeType)
                notifyDataSetChanged()
            }
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<PlantShapeInfo>() {
            override fun areItemsTheSame(
                oldItem: PlantShapeInfo,
                newItem: PlantShapeInfo,
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: PlantShapeInfo,
                newItem: PlantShapeInfo,
            ): Boolean = oldItem == newItem
        }
    }
}
