package com.greener.presentation.ui.mypage.edit

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.greener.domain.usecase.image.PickImageUseCase
import com.greener.presentation.databinding.FragmentEditProfileBinding
import com.greener.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject


@AndroidEntryPoint
class EditProfileFragment : BaseFragment<FragmentEditProfileBinding>(
    FragmentEditProfileBinding::inflate
) {
    private val viewModel: EditProfileViewModel by viewModels()
    private val args: EditProfileFragmentArgs by navArgs()

    @Inject
    lateinit var pickImageUseCase: PickImageUseCase

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        softInputAdjustResize()
        viewModel.setUserSimpleInfo(args.userSimpleInfo)
        initListener()

    }

    override fun initListener() {
        observeUserInfo()
        binding.etEditProfileNickname.doOnTextChanged { _, _, _, _ ->
            binding.btnEditProfileSaveChange.isEnabled = validateNickname()
        }
        binding.tlEditProfileNickname.setErrorIconOnClickListener {
            binding.etEditProfileNickname.text!!.clear()
        }

        binding.ivEditProfileMyProfileImg.setOnClickListener {
            viewModel.getImage(pickImageUseCase)
        }

        binding.ivEditProfileClearProfileImg.setOnClickListener {
            Log.d("확인", "클리어 클릭")
            viewModel.setProfileImage("")
        }

        binding.btnEditProfileSaveChange.setOnClickListener {

            //val realPath = absolutelyPath(viewModel.profileImage.value!!,requireContext())

            //val realPath = Uri.parse(viewModel.profileImage.value).getFilePath(requireActivity())
            //Log.d("확인", "realPath: $realPath")
            editUserProfile(viewModel.profileImage.value!!)
            //editUserProfile(realPath!!)
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
                viewModel.editUserProfile(realPath)
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

}