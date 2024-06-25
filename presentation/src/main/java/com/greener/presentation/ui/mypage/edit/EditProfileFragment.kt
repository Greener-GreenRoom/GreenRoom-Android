package com.greener.presentation.ui.mypage.edit

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.greener.domain.usecase.image.PickImageUseCase
import com.greener.domain.usecase.image.TakePictureUseCase
import com.greener.presentation.R
import com.greener.presentation.databinding.FragmentEditProfileBinding
import com.greener.presentation.model.UiState
import com.greener.presentation.ui.base.BaseFragment
import com.greener.presentation.util.ImageModalBottomSheet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class EditProfileFragment : BaseFragment<FragmentEditProfileBinding>(
    FragmentEditProfileBinding::inflate,
) {
    private val viewModel: EditProfileViewModel by viewModels()
    private val args: EditProfileFragmentArgs by navArgs()

    @Inject
    lateinit var pickImageUseCase: PickImageUseCase

    @Inject
    lateinit var takePictureUseCase: TakePictureUseCase

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        softInputAdjustResize()
        viewModel.setUserSimpleInfo(args.userSimpleInfo)
    }

    override fun initListener() {
        observeUserInfo()
        observeUiState()
        observeProfileImage()
        binding.etEditProfileNickname.doOnTextChanged { _, _, _, _ ->
            binding.btnEditProfileSaveChange.isEnabled = validateNickname()
        }
        binding.tlEditProfileNickname.setErrorIconOnClickListener {
            binding.etEditProfileNickname.text!!.clear()
        }

        binding.ivEditProfileMyProfileImg.setOnClickListener {
            val modal = ImageModalBottomSheet(
                { viewModel.getImage(pickImageUseCase) },
                { viewModel.takePicture(takePictureUseCase) },
            )
            modal.show(childFragmentManager, "")
        }

        binding.ivEditProfileClearProfileImg.setOnClickListener {
            viewModel.setProfileImage()
        }

        binding.btnEditProfileSaveChange.setOnClickListener {
            editUserProfile(viewModel.profileImage.value!!)
        }
        binding.tbEditProfileToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
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

    private fun editUserProfile(realPath: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.editUserProfile(realPath, binding.etEditProfileNickname.text.toString())
            }
        }
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    if (it == UiState.Success) {
                        moveToMyPageMain()
                    } else if (it is UiState.Error) {
                        Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun observeUserInfo() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userSimpleInfo.collect {
                    binding.vm = viewModel
                    if (it != null) {
                        viewModel.setProfileImage(it.imageUrl)
                    }
                }
            }
        }
    }

    private fun observeProfileImage() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.profileImage.collect {
                    if (it == getString(R.string.util_fail)) {
                        Toast.makeText(requireActivity(), resources.getString(R.string.image_upload_fail), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun validateNickname(): Boolean {
        val value: String = binding.etEditProfileNickname.text.toString()
        val regex = Regex("[가-힣a-zA-Z]+")

        return if (value.isEmpty() || value.length > 10) {
            binding.tlEditProfileNickname.error = " "
            binding.tlEditProfileNickname.isErrorEnabled = true
            binding.tvEditProfileNicknameRule.visibility = View.VISIBLE
            false
        } else if (!regex.matches(value)) {
            binding.tlEditProfileNickname.error = " "
            binding.tlEditProfileNickname.isErrorEnabled = true
            binding.tvEditProfileNicknameRule.visibility = View.VISIBLE
            false
        } else {
            binding.tlEditProfileNickname.error = null
            binding.tvEditProfileNicknameRule.visibility = View.INVISIBLE
            binding.tlEditProfileNickname.isErrorEnabled = false
            true
        }
    }

    private fun moveToMyPageMain() {
        findNavController().navigate(R.id.action_editProfileFragment_to_myPageMainFragment)
    }
}
