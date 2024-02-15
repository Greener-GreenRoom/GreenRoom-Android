package com.greener.presentation.ui.home.register.complete

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.greener.presentation.R
import com.greener.presentation.databinding.FragmentPlantRegistrationCompleteBinding
import com.greener.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationCompleteFragment: BaseFragment<FragmentPlantRegistrationCompleteBinding>(
    FragmentPlantRegistrationCompleteBinding::inflate
) {
    private val viewModel : RegistrationCompleteViewModel by viewModels()
    private val args : RegistrationCompleteFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        highlightNickname(args.nickname)
    }

    private fun highlightNickname(nickname: String) {
        val mainText: String = requireContext().getString(R.string.plant_registration_complete, nickname)
        val spannableStringBuilder = SpannableStringBuilder(mainText)
        spannableStringBuilder.apply {
            setSpan(
                ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.primary)),
                0,
                nickname.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        binding.tvPlantRegistrationComplete.text = spannableStringBuilder
    }
}
