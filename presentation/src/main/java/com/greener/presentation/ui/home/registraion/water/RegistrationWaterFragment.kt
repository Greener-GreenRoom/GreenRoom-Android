package com.greener.presentation.ui.home.registraion.water

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.greener.presentation.R
import com.greener.presentation.databinding.FragmentPlantRegistrationWaterBinding
import com.greener.presentation.ui.base.BaseFragment
import com.greener.presentation.ui.home.registraion.InitRegistrationIndicator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationWaterFragment: BaseFragment<FragmentPlantRegistrationWaterBinding>(
    FragmentPlantRegistrationWaterBinding::inflate
) {

    private val viewModel: RegistrationWaterViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        InitRegistrationIndicator.initRegistrationIndicator(
            binding.includePlantRegistrationWaterIndicator,
            requireContext(),
            WATERING_POSITION
        )

//        highlightNickname(args.nickname)
    }

    override fun initListener() {
        binding.tbPlantRegistrationWater.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

//    private fun highlightNickname(nickname: String) {
//        val mainText: String = requireContext().getString(R.string.plant_registration_water_cycle, nickname)
//        val spannableStringBuilder = SpannableStringBuilder(mainText)
//        spannableStringBuilder.apply {
//            setSpan(
//                ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.primary)),
//                0,
//                nickname.length,
//                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//            )
//        }
//        binding.tvPlantRegistrationWaterCycle.text = spannableStringBuilder
//    }

    companion object {
        private const val WATERING_POSITION = 3
    }
}
