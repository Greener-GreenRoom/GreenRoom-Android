package com.greener.presentation.ui.login.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.greener.presentation.R
import com.greener.presentation.databinding.FragmentLoginBinding
import com.greener.presentation.model.UiState
import com.greener.presentation.ui.base.BaseFragment
import com.greener.presentation.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(
    FragmentLoginBinding::inflate,
) {
    private val viewModel: LoginViewModel by viewModels()

    private val googleSignInClient: GoogleSignInClient by lazy { getGoogleClient() }

    private val googleAuthLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)

            try {
                val account = task.getResult(ApiException::class.java)
                val userName = account?.familyName + account.givenName
                viewModel.setEmail(account.email.toString())
                viewModel.setPhotoUrl(account.photoUrl.toString())
                viewModel.setName(userName)
                viewModel.setProvider(GOOGLE)
                getToken()
            } catch (e: ApiException) {
                Log.e("확인", e.stackTraceToString())
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vpLoginOnboarding.adapter = LoginOnBoardingVPAdapter(requireActivity())
        binding.indicatorLoginVp.attachTo(binding.vpLoginOnboarding)
    }

    override fun initListener() {
        binding.btnLoginGoogle.setOnClickListener {
            requestGoogleLogin()
        }
        binding.btnLoginTempGoNext.setOnClickListener {
            moveToRegisterNickName()
        }
        checkExistUser()
    }

    private fun checkExistUser() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect {
                when (it) {
                    is UiState.Success -> {
                        moveToMain()
                    }

                    is UiState.Fail -> {
                        moveToRegisterNickName()
                    }

                    is UiState.Error -> {
                        Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                    }

                    is UiState.Loading -> {
                    }

                    is UiState.Empty -> {
                    }
                }
            }
        }
    }

    private fun requestGoogleLogin() {
        googleSignInClient.signOut()
        val signInIntent = googleSignInClient.signInIntent
        googleAuthLauncher.launch(signInIntent)
    }

    private fun getGoogleClient(): GoogleSignInClient {
        val googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestServerAuthCode(getString(R.string.google_web_client_id))
            .requestIdToken(getString(R.string.google_web_client_id))
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(requireActivity(), googleSignInOption)
    }

    private fun moveToMain() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    private fun getToken() {
        viewModel.getToken()
    }

    private fun moveToRegisterNickName() {
        val action = LoginFragmentDirections.actionLoginFragmentToLoginRegisterNicknameFragment(
            arrayOf(viewModel.email.value, viewModel.photoUrl.value, viewModel.provider.value),
        )
        Navigation.findNavController(binding.root).navigate(action)
    }

    companion object {
        const val GOOGLE = "google"
        const val KAKAO = "kakao"
    }
}
