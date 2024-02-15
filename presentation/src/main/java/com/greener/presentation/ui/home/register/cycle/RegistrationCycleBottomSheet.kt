package com.greener.presentation.ui.home.register.cycle

import android.os.Bundle
import android.view.View
import com.greener.presentation.databinding.BottomSheetPlantRegistrationCycleBinding
import com.greener.presentation.ui.base.BaseBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationCycleBottomSheet: BaseBottomSheetFragment<BottomSheetPlantRegistrationCycleBinding>(
    BottomSheetPlantRegistrationCycleBinding::inflate
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initListener() {
    }

}
