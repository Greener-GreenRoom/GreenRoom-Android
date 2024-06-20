package com.greener.presentation.ui.home.registration.plant_shape

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.greener.domain.model.asset.PlantShapeInfo
import com.greener.presentation.R
import com.greener.presentation.databinding.ItemAssetDetailAllItemBinding
import com.greener.presentation.util.SpaceDecoration

class RegistrationAllShapeAdapter(
): ListAdapter<PlantShapeInfo, RegistrationAllShapeAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemAssetDetailAllItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemAssetDetailAllItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        private val flexboxLayout = FlexboxLayoutManager(binding.root.context).apply {
            flexWrap = FlexWrap.WRAP
        }
        init {
            binding.rvItemAssetAllItem.run {
                layoutManager = flexboxLayout
                // todo adapter
                addItemDecoration(SpaceDecoration(resources, rightDP = R.dimen.asset_view_left_padding, bottomDP = R.dimen.asset_view_bottom_padding))
            }
        }
        fun bind(item : PlantShapeInfo) {
            binding.tvItemAssetAllType.text = item.plantShapeType.name
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
            ): Boolean  = oldItem == newItem

        }
    }
}
