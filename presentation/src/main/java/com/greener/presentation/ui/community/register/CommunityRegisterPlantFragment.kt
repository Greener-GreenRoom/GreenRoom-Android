package com.greener.presentation.ui.community.register

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.greener.domain.usecase.image.PickImageUseCase
import com.greener.domain.usecase.image.TakePictureUseCase
import com.greener.presentation.R
import com.greener.presentation.databinding.FragmentCommunityRegisterPlantBinding
import com.greener.presentation.ui.base.BaseFragment
import com.greener.presentation.util.ImageModalBottomSheet
import kotlinx.coroutines.launch
import javax.inject.Inject

class CommunityRegisterPlantFragment : BaseFragment<FragmentCommunityRegisterPlantBinding>(
    FragmentCommunityRegisterPlantBinding::inflate
) {
    val viewModel: CommunityRegisterPlantViewModel by viewModels()

    @Inject
    lateinit var pickImageUseCase: PickImageUseCase

    @Inject
    lateinit var takePictureUseCase: TakePictureUseCase
    override fun initListener() {
        binding.tbCommunityRegisterPlantToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnCommunityRegisterPlantConfirm.setOnClickListener {
            findNavController().navigate(R.id.action_communityRegisterPlantFragment_to_communityRegisterCompleteFragment)
        }
        binding.btnCommunityRegisterImage.setOnClickListener {
            val modal = ImageModalBottomSheet(
                { viewModel.getImage(pickImageUseCase) },
                { viewModel.takePicture(takePictureUseCase) },
            )
            modal.show(childFragmentManager, "")
        }
    }
}