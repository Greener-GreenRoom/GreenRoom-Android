package com.greener.presentation.ui.home.decoration.main.adapter

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.greener.domain.model.asset.AssetType
import com.greener.domain.model.asset.BackgroundAccessoryInfo
import com.greener.domain.model.asset.BackgroundAccessoryType
import com.greener.domain.model.asset.PlantAccessoryInfo
import com.greener.domain.model.asset.PlantAccessoryType
import com.greener.domain.model.asset.PlantShapeInfo
import com.greener.domain.model.asset.PlantShapeType
import com.greener.presentation.R
import com.greener.presentation.databinding.ItemAssetDetailItemBinding
import com.greener.presentation.model.decoration.AssetViewItem
import com.greener.presentation.model.decoration.AssetViewObject
import jp.wasabeef.glide.transformations.BlurTransformation


class DecorationAssetViewAdapter(
    private val onClickPlantShape: (PlantShapeInfo, PlantShapeType) -> Unit,
    private val onClickPlantAccessory: (PlantAccessoryInfo, PlantAccessoryType) -> Unit,
    private val onClickBackgroundAccessory: (BackgroundAccessoryInfo, BackgroundAccessoryType) -> Unit,
    var myLevel: Int
) : ListAdapter<AssetViewItem, RecyclerView.ViewHolder>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            PLANT_SHAPE_VIEW_HOLDER -> PlantShapeViewHolder(
                ItemAssetDetailItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            PLANT_ACCESSORY_VIEW_HOLDER -> PlantAccessoryViewHolder(
                ItemAssetDetailItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            BACKGROUND_ACCESSORY_VIEW_HOLDER -> BackgroundAccessoryViewHolder(
                ItemAssetDetailItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            else -> BackgroundAccessoryViewHolder(
                ItemAssetDetailItemBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        when (holder) {
            is PlantShapeViewHolder -> {
                holder.bind(item)
            }

            is PlantAccessoryViewHolder -> {
                holder.bind(item)
            }

            is BackgroundAccessoryViewHolder -> {
                holder.bind(item)
            }
        }
    }

    override fun getItemViewType(position: Int): Int =
        when (getItem(position).assetType) {
            AssetType.PLANT_SHAPE -> PLANT_SHAPE_VIEW_HOLDER
            AssetType.PLANT_ACCESSORY -> PLANT_ACCESSORY_VIEW_HOLDER
            AssetType.BACKGROUND_ACCESSORY -> BACKGROUND_ACCESSORY_VIEW_HOLDER
        }

    @SuppressLint("notifyDataSetChanged")
    inner class PlantShapeViewHolder(
        private val binding: ItemAssetDetailItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AssetViewItem) {
            val viewObject = item.viewObject as AssetViewObject.PlantShapeObject
            Glide.with(binding.root)
                .load(viewObject.infoList.drawableID)
                .into(binding.ivItemAssetDetail)

            if (viewObject.infoList.isChecked) {
                binding.root.setBackgroundResource(R.drawable.shape_asset_on)
            } else {
                binding.root.setBackgroundResource(R.drawable.shape_asset_off)
            }

            binding.root.setOnClickListener {
                onClickPlantShape(viewObject.infoList, viewObject.infoList.plantShapeType)
                notifyDataSetChanged()
            }
        }
    }

    @SuppressLint("notifyDataSetChanged")
    inner class PlantAccessoryViewHolder(
        private val binding: ItemAssetDetailItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AssetViewItem) {
            val viewObject = item.viewObject as AssetViewObject.PlantAccessoriesObject
            val isEmpty = viewObject.infoList.drawableID == EMPTY_ACCESSORY
            val isLevelLimit = viewObject.infoList.limitLevel > myLevel

            if (isEmpty) {
                binding.tvItemAssetDetail.visibility = View.VISIBLE
                binding.tvItemAssetDetail.text = binding.root.context.getText(R.string.util_nothing)
            } else {
                if (isLevelLimit) {
                    binding.tvItemAssetDetail.visibility = View.VISIBLE
                    binding.ivItemAssetLock.visibility = View.VISIBLE
                    binding.tvItemAssetDetail.text = binding.root.context.getString(
                        R.string.decoration_level_limit,
                        viewObject.infoList.limitLevel
                    )
                    Glide.with(binding.root)
                        .asDrawable()
                        .load(viewObject.infoList.drawableID)
                        .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 3)))
                        .into(binding.ivItemAssetDetail)
                } else {
                    binding.tvItemAssetDetail.visibility = View.GONE
                    binding.ivItemAssetLock.visibility = View.GONE
                    Glide.with(binding.root)
                        .asDrawable()
                        .load(viewObject.infoList.drawableID)
                        .into(binding.ivItemAssetDetail)
                }
            }

            if (isLevelLimit.not()) {
                if (viewObject.infoList.isChecked) {
                    binding.root.setBackgroundResource(R.drawable.shape_asset_on)
                } else {
                    binding.root.setBackgroundResource(R.drawable.shape_asset_off)
                }

                binding.root.setOnClickListener {
                    onClickPlantAccessory(viewObject.infoList, viewObject.infoList.itemType)
                    notifyDataSetChanged()
                }
            }
        }
    }

    @SuppressLint("notifyDataSetChanged")
    inner class BackgroundAccessoryViewHolder(
        private val binding: ItemAssetDetailItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AssetViewItem) {
            val viewObject = item.viewObject as AssetViewObject.BackgroundAccessoriesObject
            val isEmpty = viewObject.infoList.drawableID == EMPTY_ACCESSORY
            val isLevelLimit = viewObject.infoList.limitLevel > myLevel

            if (isEmpty) {
                binding.tvItemAssetDetail.visibility = View.VISIBLE
                binding.tvItemAssetDetail.text = binding.root.context.getText(R.string.util_nothing)
            } else {
                if (isLevelLimit) {
                    binding.tvItemAssetDetail.visibility = View.VISIBLE
                    binding.ivItemAssetLock.visibility = View.VISIBLE
                    binding.tvItemAssetDetail.text = binding.root.context.getString(
                        R.string.decoration_level_limit,
                        viewObject.infoList.limitLevel
                    )
                    Glide.with(binding.root)
                        .asDrawable()
                        .load(viewObject.infoList.drawableID)
                        .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 3)))
                        .into(binding.ivItemAssetDetail)
                } else {
                    binding.tvItemAssetDetail.visibility = View.GONE
                    binding.ivItemAssetLock.visibility = View.GONE
                    Glide.with(binding.root)
                        .asDrawable()
                        .load(viewObject.infoList.drawableID)
                        .into(binding.ivItemAssetDetail)
                }
            }

            if (isLevelLimit.not()) {
                if (viewObject.infoList.isChecked) {
                    binding.root.setBackgroundResource(R.drawable.shape_asset_on)
                } else {
                    binding.root.setBackgroundResource(R.drawable.shape_asset_off)
                }

                binding.root.setOnClickListener {
                    onClickBackgroundAccessory(viewObject.infoList, viewObject.infoList.itemType)
                    notifyDataSetChanged()
                }
            }
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<AssetViewItem>() {
            override fun areItemsTheSame(oldItem: AssetViewItem, newItem: AssetViewItem): Boolean {
                return oldItem.assetType == newItem.assetType
            }

            override fun areContentsTheSame(
                oldItem: AssetViewItem,
                newItem: AssetViewItem
            ): Boolean {
                return oldItem == newItem
            }
        }

        private const val PLANT_SHAPE_VIEW_HOLDER = 0
        private const val PLANT_ACCESSORY_VIEW_HOLDER = 1
        private const val BACKGROUND_ACCESSORY_VIEW_HOLDER = 2

        private const val EMPTY_ACCESSORY = 0
    }
}
