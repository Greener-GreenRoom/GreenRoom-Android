package com.greener.presentation.ui.home.decoration.plant

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.greener.presentation.R
import com.greener.presentation.databinding.ViewGreenRoomPlantPreviewBinding
import com.greener.presentation.model.decoration.PlantDecorationDetailInfo
import com.greener.presentation.ui.base.BaseFragment

class DecorationPlantPreviewFragment(
    private var plantDecorationInfo: PlantDecorationDetailInfo
) : BaseFragment<ViewGreenRoomPlantPreviewBinding>(
    ViewGreenRoomPlantPreviewBinding::inflate,
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updatePlantDecoration(plantDecorationInfo)
    }

    private fun updatePlantDecoration(info: PlantDecorationDetailInfo) {
        Glide.with(binding.root)
            .load(info.shape?.drawableID)
            .placeholder(R.drawable.asset_plant_main_character)
            .into(binding.ivPreviewPlantShape)

        if (info.glasses?.drawableID != 0) {
            Glide.with(binding.root)
                .load(info.glasses?.drawableID)
                .into(binding.ivPreviewPlantAccessoryEye)

        }

        if (info.hairAccessory?.drawableID != 0) {
            Glide.with(binding.root)
                .load(info.hairAccessory?.drawableID)
                .into(binding.ivPreviewPlantAccessoryHead)
        }

    }
}
