package com.greener.presentation.ui.home.registration.complete

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.greener.presentation.R
import com.greener.presentation.databinding.FragmentPlantRegistrationCompleteBinding
import com.greener.presentation.ui.base.BaseFragment
import com.greener.presentation.util.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class RegistrationCompleteFragment : BaseFragment<FragmentPlantRegistrationCompleteBinding>(
    FragmentPlantRegistrationCompleteBinding::inflate,
) {
    @Inject
    lateinit var viewModelFactory: RegistrationCompleteViewModel.PlantRegistrationInfoFactory

    private val args: RegistrationCompleteFragmentArgs by navArgs()
    private val viewModel: RegistrationCompleteViewModel by viewModels {
        RegistrationCompleteViewModel.provideFactory(viewModelFactory, args.PlantRegistrationInfo)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        protectBackPressed()

        args.PlantRegistrationInfo.nickname?.let {
            highlightNickname(it)
        }
    }

    override fun initCollector() {
        repeatOnStarted(viewLifecycleOwner) {
            viewModel.event.collectLatest { event ->
                handleEvent(event)
            }
        }
    }

    private fun highlightNickname(nickname: String) {
        val mainText: String = requireContext().getString(R.string.plant_registration_complete, nickname)
        val spannableStringBuilder = SpannableStringBuilder(mainText)
        spannableStringBuilder.apply {
            setSpan(
                ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.primary)),
                0,
                nickname.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE,
            )
        }
        binding.tvPlantRegistrationComplete.text = spannableStringBuilder
    }

    private fun protectBackPressed() {
        // Todo 디자인 팀이랑 협의 필요
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Toast.makeText(requireContext(), "이미 등록이 완료되었습니다. 수정을 원하시면 컬렉션에서 진행해 주세요", Toast.LENGTH_SHORT).show()
                }
            },
        )
    }

    private fun handleEvent(event: RegistrationCompleteViewModel.Event) {
        when (event) {
            is RegistrationCompleteViewModel.Event.PrintMyPlantShape -> {
                Glide.with(requireContext())
                    .load(event.plantShapeDrawableID)
                    .into(binding.ivPlantRegistrationComplete)
            }

            RegistrationCompleteViewModel.Event.MoveToHome -> {
                val action = RegistrationCompleteFragmentDirections.actionRegistrationCompleteFragmentToHomeFragment()
                findNavController().navigate(action)
            }
        }
    }
}
