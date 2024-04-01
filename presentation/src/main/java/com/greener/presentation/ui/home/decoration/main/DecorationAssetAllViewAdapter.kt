package com.greener.presentation.ui.home.decoration.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.greener.domain.model.asset.AssetType
import com.greener.presentation.R
import com.greener.presentation.databinding.ItemAssetDetailAllItemBinding
import com.greener.presentation.model.decoration.AllAssetViewItem
import com.greener.presentation.model.decoration.AllAssetViewObject
import com.greener.presentation.model.decoration.AssetViewItem
import com.greener.presentation.model.decoration.AssetViewObject
import com.greener.presentation.util.SpaceDecoration


class DecorationAssetAllViewAdapter(
): ListAdapter<AllAssetViewItem, RecyclerView.ViewHolder>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ALL_PLANT_SHAPE_VIEW_HOLDER -> { AllPlantShapeViewHolder(ItemAssetDetailAllItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))}
            ALL_PLANT_ACCESSORY_VIEW_HOLDER -> { AllPlantAccessoryViewHolder(ItemAssetDetailAllItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))}
            ALL_BACKGROUND_ACCESSORY_VIEW_HOLDER -> { AllBackgroundAccessoryViewHolder(ItemAssetDetailAllItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))}
            else -> { AllBackgroundAccessoryViewHolder(ItemAssetDetailAllItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))}

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        when (holder) {
            is AllPlantShapeViewHolder -> { holder.bind(item) }
            is AllPlantAccessoryViewHolder -> { holder.bind(item) }
            is AllBackgroundAccessoryViewHolder -> {holder.bind(item)}
        }
    }

    override fun getItemViewType(position: Int): Int =
        when (getItem(position).assetType) {
            AssetType.PLANT_SHAPE -> ALL_PLANT_SHAPE_VIEW_HOLDER
            AssetType.PLANT_ACCESSORY -> ALL_PLANT_ACCESSORY_VIEW_HOLDER
            AssetType.BACKGROUND_ACCESSORY -> ALL_BACKGROUND_ACCESSORY_VIEW_HOLDER
        }

    inner class AllPlantShapeViewHolder(
        private val binding : ItemAssetDetailAllItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        private val flexboxLayout = FlexboxLayoutManager(binding.root.context)
        private val decorationAssetViewAdapter = DecorationAssetViewAdapter()
        init {
            flexboxLayout.justifyContent = JustifyContent.CENTER
        }

        fun bind(item: AllAssetViewItem){
            val viewObject = item.viewObject as AllAssetViewObject.AllPlantShapeObject
            val itemList = emptyList<AssetViewItem>().toMutableList()
            viewObject.infoList.forEach {
                itemList.add(
                    AssetViewItem(AssetType.PLANT_SHAPE, AssetViewObject.PlantShapeObject(it))
                )
            }

            binding.tvItemAssetAllType.text = binding.root.context.getText(viewObject.plantShapeTypeCode)
            binding.rvItemAssetAllItem.run {
                layoutManager = flexboxLayout
                adapter = decorationAssetViewAdapter
                addItemDecoration(SpaceDecoration(resources, rightDP = R.dimen.asset_view_left_padding, bottomDP = R.dimen.asset_view_bottom_padding))
            }
            decorationAssetViewAdapter.submitList(itemList)
        }
    }

    inner class AllPlantAccessoryViewHolder(
        private val binding : ItemAssetDetailAllItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        private val flexboxLayout = FlexboxLayoutManager(binding.root.context)
        private val decorationAssetViewAdapter = DecorationAssetViewAdapter()
        init {
            flexboxLayout.justifyContent = JustifyContent.CENTER
        }
        fun bind(item: AllAssetViewItem){
            val viewObject = item.viewObject as AllAssetViewObject.AllPlantAccessoriesObject
            val itemList = emptyList<AssetViewItem>().toMutableList()
            viewObject.infoList.forEach {
                itemList.add(
                    AssetViewItem(AssetType.PLANT_ACCESSORY, AssetViewObject.PlantAccessoriesObject(it))
                )
            }
            binding.tvItemAssetAllType.text = binding.root.context.getText(viewObject.plantAccessoryTypeCode)
            binding.rvItemAssetAllItem.run {
                layoutManager = flexboxLayout
                adapter = decorationAssetViewAdapter
                addItemDecoration(SpaceDecoration(resources, rightDP = R.dimen.asset_view_left_padding, bottomDP = R.dimen.asset_view_bottom_padding))
            }
            decorationAssetViewAdapter.submitList(itemList)
        }
    }

    inner class AllBackgroundAccessoryViewHolder(
        private val binding : ItemAssetDetailAllItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        private val flexboxLayout = FlexboxLayoutManager(binding.root.context)
        private val decorationAssetViewAdapter = DecorationAssetViewAdapter()
        init {
            flexboxLayout.justifyContent = JustifyContent.CENTER
        }
        fun bind(item: AllAssetViewItem){
            val viewObject = item.viewObject as AllAssetViewObject.AllBackgroundAccessoriesObject
            val itemList = emptyList<AssetViewItem>().toMutableList()
            viewObject.infoList.forEach {
                itemList.add(
                    AssetViewItem(AssetType.BACKGROUND_ACCESSORY, AssetViewObject.BackgroundAccessoriesObject(it))
                )
            }
            binding.tvItemAssetAllType.text = binding.root.context.getText(viewObject.backgroundAccessoryTypeCode)
            binding.rvItemAssetAllItem.run {
                layoutManager = flexboxLayout
                adapter = decorationAssetViewAdapter
                addItemDecoration(SpaceDecoration(resources, rightDP = R.dimen.asset_view_left_padding, bottomDP = R.dimen.asset_view_bottom_padding))
            }
            decorationAssetViewAdapter.submitList(itemList)
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<AllAssetViewItem>() {
            override fun areItemsTheSame(oldItem: AllAssetViewItem, newItem: AllAssetViewItem): Boolean {
                return oldItem.assetType == newItem.assetType
            }

            override fun areContentsTheSame(oldItem: AllAssetViewItem, newItem: AllAssetViewItem): Boolean {
                return oldItem == newItem
            }
        }

        private const val ALL_PLANT_SHAPE_VIEW_HOLDER = 0
        private const val ALL_PLANT_ACCESSORY_VIEW_HOLDER = 1
        private const val ALL_BACKGROUND_ACCESSORY_VIEW_HOLDER = 2
    }
}
