package com.greener.presentation.ui.home.decoration.plant

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.greener.domain.model.home.PlantDecorationInfo
import com.greener.presentation.databinding.ViewGreenRoomPlantPreviewBinding
import com.greener.presentation.model.decoration.ChoicePlant
import com.greener.presentation.model.decoration.PlantDecorationIdInfo
import com.greener.presentation.ui.base.BaseFragment

class DecorationPlantPreviewFragment(
    var plantDecorationIdInfo: PlantDecorationIdInfo
) : BaseFragment<ViewGreenRoomPlantPreviewBinding>(
    ViewGreenRoomPlantPreviewBinding::inflate,
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updatePlantDecoration(plantDecorationIdInfo)
    }

    fun updatePlantDecoration(info: PlantDecorationIdInfo) {
        Glide.with(binding.root)
            .load(info.shape)
            .into(binding.ivPreviewPlantShape)

        Glide.with(binding.root)
            .load(info.glasses)
            .into(binding.ivPreviewPlantAccessoryEye)

        Glide.with(binding.root)
            .load(info.hairAccessory)
            .into(binding.ivPreviewPlantAccessoryHead)
    }
}
