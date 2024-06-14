package com.greener.presentation.ui.home.decoration.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.greener.domain.model.asset.AssetDetailTypeInfo
import com.greener.domain.model.asset.AssetType
import com.greener.presentation.databinding.ItemAssetDetailTypeDarkBinding

class DecorationAssetDetailTypeAdapter(
    private val changeCheck : (AssetType, Int) -> Unit
): ListAdapter<AssetDetailTypeInfo, DecorationAssetDetailTypeAdapter.ViewHolder>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemAssetDetailTypeDarkBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class ViewHolder(
        private val binding: ItemAssetDetailTypeDarkBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                changeCheck( getItem(absoluteAdapterPosition).assetType, getItem(absoluteAdapterPosition).id )
                notifyDataSetChanged()
            }
        }

        fun bind(item : AssetDetailTypeInfo) {
            binding.info = item
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<AssetDetailTypeInfo>() {
            override fun areItemsTheSame(
                oldItem: AssetDetailTypeInfo,
                newItem: AssetDetailTypeInfo
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: AssetDetailTypeInfo,
                newItem: AssetDetailTypeInfo
            ): Boolean =
                oldItem == newItem
        }
    }
}
