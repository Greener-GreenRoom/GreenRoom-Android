package com.greener.presentation.ui.home.registration.nickname_image

import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.greener.domain.usecase.image.PickImageUseCase
import com.greener.domain.usecase.image.TakePictureUseCase
import com.greener.presentation.R
import com.greener.presentation.databinding.FragmentPlantRegistrationNicknameImageBinding
import com.greener.presentation.ui.base.BaseFragment
import com.greener.presentation.ui.home.registration.InitRegistrationIndicator
import com.greener.presentation.ui.home.registration.bottom_sheet.RegistrationGetImageBottomSheet
import com.greener.presentation.util.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class RegistrationNicknameImageFragment : BaseFragment<FragmentPlantRegistrationNicknameImageBinding> (
    FragmentPlantRegistrationNicknameImageBinding::inflate,
) {
    private val viewModel: RegistrationNicknameImageViewModel by viewModels()
    private val args: RegistrationNicknameImageFragmentArgs by navArgs()

    private val modal = RegistrationGetImageBottomSheet({ viewModel.getImage(pickImageUseCase) }, { viewModel.takePicture(takePictureUseCase) })

    @Inject
    lateinit var pickImageUseCase: PickImageUseCase

    @Inject
    lateinit var takePictureUseCase: TakePictureUseCase

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
        highlightNicknameStarColor()
    }

    override fun initListener() {
        binding.tbPlantRegistrationNicknameImage.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnPlantRegistrationNicknameImagePlant.setOnClickListener {
            showGetImageBottomSheet()
        }
    }

    override fun initCollector() {
        repeatOnStarted(viewLifecycleOwner) {
            viewModel.inputNickname.collectLatest {
                viewModel.checkGoodNickname(it)
            }
        }

        repeatOnStarted(viewLifecycleOwner) {
            viewModel.stateOfNickname.collectLatest { state ->
                when (state) {
                    RegistrationNicknameImageViewModel.StateOfNickname.Duplicate -> {
                        binding.tlPlantRegistrationNicknameImage.boxStrokeColor = requireContext().getColor(R.color.red100)
                        binding.tvPlantRegistrationNicknameImagePlantNicknameHint.text = getText(R.string.plant_registration_nickname_duplicate)
                        binding.btnPlantRegistrationGoNext.setBackgroundColor(requireContext().getColor(R.color.gray200))
                    }
                    RegistrationNicknameImageViewModel.StateOfNickname.SpecialChar -> {
                        binding.tlPlantRegistrationNicknameImage.boxStrokeColor = requireContext().getColor(R.color.red100)
                        binding.tvPlantRegistrationNicknameImagePlantNicknameHint.text = getText(R.string.plant_registration_nickname_warning)
                        binding.btnPlantRegistrationGoNext.setBackgroundColor(requireContext().getColor(R.color.gray200))
                    }
                    RegistrationNicknameImageViewModel.StateOfNickname.TooLong -> {
                        binding.tlPlantRegistrationNicknameImage.boxStrokeColor = requireContext().getColor(R.color.red100)
                        binding.tvPlantRegistrationNicknameImagePlantNicknameHint.text = getText(R.string.plant_registration_nickname_warning)
                        binding.btnPlantRegistrationGoNext.setBackgroundColor(requireContext().getColor(R.color.gray200))
                    }
                    RegistrationNicknameImageViewModel.StateOfNickname.Blank -> {
                        binding.tlPlantRegistrationNicknameImage.boxStrokeColor = requireContext().getColor(R.color.red100)
                        binding.tvPlantRegistrationNicknameImagePlantNicknameHint.text = getText(R.string.plant_registration_nickname_warning)
                        binding.btnPlantRegistrationGoNext.setBackgroundColor(requireContext().getColor(R.color.gray200))
                    }
                    else -> {
                        binding.tvPlantRegistrationNicknameImagePlantNicknameHint.text = null
                        binding.tlPlantRegistrationNicknameImage.boxStrokeColor = requireContext().getColor(R.color.gray400)
                        binding.btnPlantRegistrationGoNext.setBackgroundColor(
                            requireContext().getColor(
                                R.color.primary,
                            ),
                        )
                        binding.btnPlantRegistrationGoNext.setOnClickListener {
                            viewModel.moveToWatering()
                        }
                    }
                }
            }
        }

        repeatOnStarted(viewLifecycleOwner) {
            viewModel.plantImage.collectLatest {
                if (it.isNotBlank()) {
                    Glide.with(requireContext())
                        .load(it)
                        .into(binding.ivPlantRegistrationNicknameImage)
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

    private fun showGetImageBottomSheet() {
        if (modal.isAdded.not()) {
            modal.show(childFragmentManager, BOTTOM_SHEET)
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
            RegistrationNicknameImageViewModel.Event.ShowGetImageBottomSheet -> { showGetImageBottomSheet() }
        }
    }

    companion object {
        private const val NICKNAME_IMAGE_POSITION = 2
        private const val BOTTOM_SHEET = "Plant Registration Get Image"
    }
}
