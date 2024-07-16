package com.greener.presentation.ui.home.registration.plant_shape

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.greener.domain.model.asset.PlantShapeInfo
import com.greener.domain.model.asset.PlantShapeType
import com.greener.presentation.R
import com.greener.presentation.databinding.ItemAssetDetailAllItemBinding
import com.greener.presentation.model.decoration.AllAssetViewObject
import com.greener.presentation.util.SpaceDecoration

class RegistrationAllShapeAdapter(
    private val onClickPlantShape: (PlantShapeInfo, PlantShapeType) -> Unit,
) : ListAdapter<AllAssetViewObject.AllPlantShapeObject, RegistrationAllShapeAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemAssetDetailAllItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemAssetDetailAllItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        private val flexboxLayout = FlexboxLayoutManager(binding.root.context).apply {
            flexWrap = FlexWrap.WRAP
        }
        private val registrationShapeAdapter = RegistrationShapeAdapter(onClickPlantShape)
        init {
            binding.rvItemAssetAllItem.run {
                layoutManager = flexboxLayout
                adapter = registrationShapeAdapter
                addItemDecoration(SpaceDecoration(resources, rightDP = R.dimen.asset_view_left_padding, bottomDP = R.dimen.asset_view_bottom_padding))
            }
        }
        fun bind(item: AllAssetViewObject.AllPlantShapeObject) {
            binding.tvItemAssetAllType.text = binding.root.context.getText(item.plantShapeTypeCode)
            registrationShapeAdapter.submitList(item.infoList)
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<AllAssetViewObject.AllPlantShapeObject>() {
            override fun areItemsTheSame(
                oldItem: AllAssetViewObject.AllPlantShapeObject,
                newItem: AllAssetViewObject.AllPlantShapeObject,
            ): Boolean =
                oldItem.plantShapeTypeCode == newItem.plantShapeTypeCode

            override fun areContentsTheSame(
                oldItem: AllAssetViewObject.AllPlantShapeObject,
                newItem: AllAssetViewObject.AllPlantShapeObject,
            ): Boolean = oldItem == newItem
        }
    }
}
