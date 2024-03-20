package com.greener.presentation.ui.home.registraion.nickname_image

import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.greener.presentation.R
import com.greener.presentation.databinding.FragmentPlantRegistrationNicknameImageBinding
import com.greener.presentation.ui.base.BaseFragment
import com.greener.presentation.ui.home.registraion.InitRegistrationIndicator
import com.greener.presentation.util.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class RegistrationNicknameImageFragment : BaseFragment<FragmentPlantRegistrationNicknameImageBinding> (
    FragmentPlantRegistrationNicknameImageBinding::inflate,
) {
    private val viewModel: RegistrationNicknameImageViewModel by viewModels()
    private val args: RegistrationNicknameImageFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        viewModel.initNavArgsData(args.PlantRegistrationInfo)

        InitRegistrationIndicator.initRegistrationIndicator(
            binding.includePlantRegistrationNicknameImageIndicator,
            requireContext(),
            NICKNAME_IMAGE_POSITION,
        )

        softInputAdjustResize()

        // todo 사진 불러오기
//        Glide.with(this)
//            .load(R.drawable.img_temp_holder_face)
//            .into(binding.ivPlantRegistrationNicknameImagePlant)

        highlightNicknameStarColor()
    }

    override fun initListener() {
        binding.tbPlantRegistrationNicknameImage.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.tePlantRegistrationNicknameImage.setOnEditorActionListener { _, action, _ ->
            var handled = false
            if (action == EditorInfo.IME_ACTION_DONE) {
                viewModel.inputPlantNickname()
                handled = true
            }
            handled
        }
    }

    override fun initCollector() {
        repeatOnStarted(viewLifecycleOwner) {
            viewModel.plantRegistrationInfo.collectLatest { info ->
                if (info?.nickname.isNullOrBlank().not()) {
                    binding.btnPlantRegistrationGoNext.setBackgroundColor(requireContext().getColor(R.color.primary))
                    binding.btnPlantRegistrationGoNext.setOnClickListener {
                        viewModel.moveToWatering()
                    }
                } else {
                    binding.btnPlantRegistrationGoNext.setBackgroundColor(requireContext().getColor(R.color.gray200))
                }
            }
        }

        repeatOnStarted(viewLifecycleOwner) {
            viewModel.event.collectLatest { event ->
                handleEvent(event)
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
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE,
            )
        }
        binding.tvPlantRegistrationNicknameImagePlantNickname.text = spannableStringBuilder
    }

    private fun softInputAdjustResize() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireActivity().window.setDecorFitsSystemWindows(false)
            binding.root.setOnApplyWindowInsetsListener { _, insets ->
                val topInset = insets.getInsets(WindowInsets.Type.statusBars()).top
                val imeHeight = insets.getInsets(WindowInsets.Type.ime()).bottom
                val navigationHeight = insets.getInsets(WindowInsets.Type.navigationBars()).bottom
                val bottomInset = if (imeHeight == 0) navigationHeight else imeHeight
                binding.root.setPadding(0, topInset, 0, bottomInset)
                insets
            }
        } else {
            requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }
    }

    private fun handleEvent(event: RegistrationNicknameImageViewModel.Event) {
        when (event) {
            is RegistrationNicknameImageViewModel.Event.MoveToWatering -> {
                val action = RegistrationNicknameImageFragmentDirections.actionRegistrationNicknameImageFragmentToRegistrationWaterFragment(
                    event.plantRegistrationInfo,
                )
                findNavController().navigate(action)
            }
        }
    }

    companion object {
        private const val NICKNAME_IMAGE_POSITION = 2
    }
}
