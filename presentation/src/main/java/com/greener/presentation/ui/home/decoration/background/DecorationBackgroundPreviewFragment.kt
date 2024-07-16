package com.greener.presentation.ui.home.decoration.background

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.greener.presentation.databinding.ViewGreenRoomBackgroundPreviewBinding
import com.greener.presentation.model.decoration.PlantDecorationDetailInfo
import com.greener.presentation.ui.base.BaseFragment

class DecorationBackgroundPreviewFragment(
    private val plantDecorationDetailInfo: PlantDecorationDetailInfo,
) : BaseFragment<ViewGreenRoomBackgroundPreviewBinding>(
    ViewGreenRoomBackgroundPreviewBinding::inflate,
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateBackgroundDecoration(plantDecorationDetailInfo)
    }

    private fun updateBackgroundDecoration(info: PlantDecorationDetailInfo) {
        Glide.with(binding.root)
            .load(info.backgroundShelf?.viewDrawableId)
            .into(binding.ivPreviewBackgroundShelf)

        Glide.with(binding.root)
            .load(info.backgroundWindow?.viewDrawableId)
            .into(binding.ivPreviewBackgroundGlass)
    }
}
