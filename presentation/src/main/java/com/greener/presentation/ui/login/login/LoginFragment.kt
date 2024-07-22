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
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
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
    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e("확인", "카카오계정으로 로그인 실패", error)
        } else if (token != null) {
            Log.i("확인", "카카오계정으로 로그인 성공 ${token.accessToken}")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vpLoginOnboarding.adapter = LoginOnBoardingVPAdapter(requireActivity())
        binding.indicatorLoginVp.attachTo(binding.vpLoginOnboarding)
    }

    override fun initListener() {
        val context = requireActivity()
        binding.btnLoginGoogle.setOnClickListener {
            requestGoogleLogin()
        }
        checkExistUser()


        binding.btnLoginKakao.setOnClickListener {

            // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
                UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                    if (error != null) {
                        Log.e("확인", "카카오톡으로 로그인 실패", error)

                        // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                        // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            return@loginWithKakaoTalk
                        }

                        // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                        UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                    } else if (token != null) {
                        Log.i("확인", "카카오톡으로 로그인 성공 ${token.accessToken}")
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                UserApiClient.instance.me { user, error ->
                    if (error != null) {
                        Log.e("확인", "사용자 정보 요청 실패", error)
                    } else if (user != null) {
                        Log.i(
                            "확인", "사용자 정보 요청 성공" +
                                    "\n회원번호: ${user.id}" +
                                    "\n이메일: ${user.kakaoAccount?.email}" +
                                    "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                                    "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}"
                        )
                        viewModel.setEmail(user.kakaoAccount?.email!!)
                        viewModel.setPhotoUrl(user.kakaoAccount?.profile?.thumbnailImageUrl!!)
                        viewModel.setName(user.kakaoAccount?.profile?.nickname!!)
                        viewModel.setProvider(KAKAO)
                    }
                }
            }
            getToken()
        }
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
