package com.greener.presentation.ui.home.registration.bottom_sheet

import com.greener.presentation.databinding.BottomSheetPlantRegistrationImageBinding
import com.greener.presentation.ui.base.BaseBottomSheetFragment

class RegistrationGetImageBottomSheet(
    val pickImage: () -> Unit,
    val takePicture: () -> Unit,
) : BaseBottomSheetFragment<BottomSheetPlantRegistrationImageBinding>(
    BottomSheetPlantRegistrationImageBinding::inflate,
) {
    override fun initListener() {
        binding.btnPlantRegistrationImageChoosePicture.setOnClickListener {
            pickImage()
            dismiss()
        }

        binding.btnPlantRegistrationImageTakePicture.setOnClickListener {
            takePicture()
            dismiss()
        }
    }
}
