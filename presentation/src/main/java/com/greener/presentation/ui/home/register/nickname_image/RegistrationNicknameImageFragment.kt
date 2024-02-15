package com.greener.presentation.ui.home.register.nickname_image

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.greener.presentation.R
import com.greener.presentation.databinding.FragmentPlantRegistrationNicknameImageBinding
import com.greener.presentation.ui.base.BaseFragment
import com.greener.presentation.ui.home.register.InitRegistrationIndicator
import com.greener.presentation.util.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class RegistrationNicknameImageFragment: BaseFragment<FragmentPlantRegistrationNicknameImageBinding> (
    FragmentPlantRegistrationNicknameImageBinding::inflate
) {
    val viewModel : RegistrationNicknameImageViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        InitRegistrationIndicator.initRegistrationIndicator(
            binding.includePlantRegistrationNicknameImageIndicator,
            requireContext(),
            NICKNAME_IMAGE_POSITION
        )

        // todo 사진 불러오기
        Glide.with(this)
            .load(R.drawable.img_temp_holder_face)
            .into(binding.ivPlantRegistrationNicknameImagePlant)

        highlightNicknameStarColor()
    }

    override fun initListener() {
        binding.tbPlantRegistrationNicknameImage.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun initCollector() {
        repeatOnStarted(viewLifecycleOwner) {
            viewModel.inputNickname.collectLatest { nickname ->
                if (nickname.isNotBlank()) {
                    binding.btnPlantRegistrationGoNext.setBackgroundColor(requireContext().getColor(R.color.primary))

                    binding.btnPlantRegistrationGoNext.setOnClickListener {

                    }
                } else {
                    binding.btnPlantRegistrationGoNext.setBackgroundColor(requireContext().getColor(R.color.gray200))
                }
            }
        }
    }

    private fun highlightNicknameStarColor() {
        val mainText: String = requireContext().getString(R.string.plant_registration_plant_nickname)
        val spannableStringBuilder = SpannableStringBuilder(mainText)
        spannableStringBuilder.apply {
            setSpan(
                ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.red100)),
                    mainText.length.minus(1),
                    mainText.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        binding.tvPlantRegistrationNicknameImagePlantNickname.text = spannableStringBuilder
    }

    companion object {
        private const val NICKNAME_IMAGE_POSITION = 2
    }
}
