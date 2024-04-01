package com.greener.presentation.ui.home.decoration.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.greener.domain.model.asset.AssetType
import com.greener.presentation.databinding.ItemAssetDetailAllItemBinding
import com.greener.presentation.databinding.ItemAssetDetailItemBinding
import com.greener.presentation.model.decoration.AllAssetViewItem
import com.greener.presentation.model.decoration.AllAssetViewObject
import com.greener.presentation.model.decoration.AssetViewItem
import com.greener.presentation.model.decoration.AssetViewObject


class DecorationAssetViewAdapter(
): ListAdapter<AssetViewItem, RecyclerView.ViewHolder>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            PLANT_SHAPE_VIEW_HOLDER -> PlantShapeViewHolder(ItemAssetDetailItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            PLANT_ACCESSORY_VIEW_HOLDER -> PlantAccessoryViewHolder(ItemAssetDetailItemBinding.inflate(LayoutInflater.from(parent.context),parent, false))
            BACKGROUND_ACCESSORY_VIEW_HOLDER -> BackgroundAccessoryViewHolder(ItemAssetDetailItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> BackgroundAccessoryViewHolder(ItemAssetDetailItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        when (holder) {
            is PlantShapeViewHolder -> { holder.bind(item) }
            is PlantAccessoryViewHolder -> { holder.bind(item) }
            is BackgroundAccessoryViewHolder -> { holder.bind(item) }
        }
    }

    override fun getItemViewType(position: Int): Int =
        when (getItem(position).assetType) {
            AssetType.PLANT_SHAPE -> PLANT_SHAPE_VIEW_HOLDER
            AssetType.PLANT_ACCESSORY -> PLANT_ACCESSORY_VIEW_HOLDER
            AssetType.BACKGROUND_ACCESSORY -> BACKGROUND_ACCESSORY_VIEW_HOLDER
        }

    inner class PlantShapeViewHolder(
        private val binding : ItemAssetDetailItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AssetViewItem){
            val viewObject = item.viewObject as AssetViewObject.PlantShapeObject
            Glide.with(binding.root)
                .load(viewObject.infoList.drawableID)
                .into(binding.ivItemAssetDetail)
        }
    }

    inner class PlantAccessoryViewHolder(
        private val binding : ItemAssetDetailItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AssetViewItem){
            val viewObject = item.viewObject as AssetViewObject.PlantAccessoriesObject
            Glide.with(binding.root)
                .load(viewObject.infoList.drawableID)
                .into(binding.ivItemAssetDetail)
        }
    }

    inner class BackgroundAccessoryViewHolder(
        private val binding : ItemAssetDetailItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AssetViewItem){
            val viewObject = item.viewObject as AssetViewObject.BackgroundAccessoriesObject
            Glide.with(binding.root)
                .load(viewObject.infoList.drawableID)
                .into(binding.ivItemAssetDetail)
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<AssetViewItem>() {
            override fun areItemsTheSame(oldItem: AssetViewItem, newItem: AssetViewItem): Boolean {
                return oldItem.assetType == newItem.assetType
            }

            override fun areContentsTheSame(oldItem: AssetViewItem, newItem: AssetViewItem): Boolean {
                return oldItem == newItem
            }
        }
        private const val PLANT_SHAPE_VIEW_HOLDER = 0
        private const val PLANT_ACCESSORY_VIEW_HOLDER = 1
        private const val BACKGROUND_ACCESSORY_VIEW_HOLDER = 2
    }
}
