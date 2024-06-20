package com.greener.presentation.ui.mypage.main

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.greener.presentation.R
import com.greener.presentation.databinding.FragmentMyPageMainBinding
import com.greener.presentation.model.UiState
import com.greener.presentation.ui.base.BaseFragment
import com.greener.presentation.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyPageMainFragment : BaseFragment<FragmentMyPageMainBinding>(
    FragmentMyPageMainBinding::inflate,
) {

    private val viewModel: MyPageMainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        softInputAdjustResize()
        initListener()
    }
    override fun initListener() {
        viewModel.getMyPageInfo()
        observeMyInfo()
        binding.tvMyPageMainPushNotificationSetting.setOnClickListener {
            moveToEditPush()
        }
        binding.tvMyPageMainToLevel.setOnClickListener {
            moveToMyPageLevel()
        }
        binding.tbMyPageToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.ivMyPageMainToEditProfile.setOnClickListener {
            moveToEditProfile()
        }
        binding.tvMyPageMainTermsOfService.setOnClickListener {
        }
        binding.tvMyPageMainLogout.setOnClickListener {
            showLogoutDialog()
        }
        binding.tvMyPageMainWithdraw.setOnClickListener {
            moveToWithdraw()
        }
        binding.tvMyPageMainSuggest.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(TALLY_SUGGEST))
            startActivity(browserIntent)
        }
        binding.tvMyPageMainAppVersionTitle.isClickable
    }

    private fun observeMyInfo() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    if (it == UiState.Success) {
                        binding.vm = viewModel
                    } else if (it is UiState.Error) {
                        Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
    private fun showLogoutDialog() {
        val dialog = LogoutDialog(requireActivity())

        dialog.setItemClickListener(object : LogoutDialog.ClickListener {
            override fun onClick() {
                logout()
            }
        })
        dialog.show()
    }

    private fun logout() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
        val mGoogleSignInClient = this.let { GoogleSignIn.getClient(requireActivity(), gso) }

        mGoogleSignInClient.signOut()
            .addOnCompleteListener {
                viewModel.logout()
                moveToSignActivity()
            }
    }
    private fun moveToSignActivity() {
        val intent = Intent(requireActivity(), LoginActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    private fun moveToMyPageLevel() {
        findNavController().navigate(R.id.action_myPageMainFragment_to_myPageLevelFragment)
    }

    private fun moveToEditPush() {
        findNavController().navigate(R.id.action_myPageMainFragment_to_editPushFragment)
    }

    private fun moveToEditProfile() {
        val action = MyPageMainFragmentDirections.actionMyPageMainFragmentToEditProfileFragment(viewModel.userSimpleInfo.value!!)
        findNavController().navigate(action)
    }

    private fun moveToWithdraw() {
        findNavController().navigate(R.id.action_myPageMainFragment_to_userWithdrawReasonFragment)
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
    companion object {
        const val TALLY_SUGGEST = "https://tally.so/r/nGzplZ"
    }
}
