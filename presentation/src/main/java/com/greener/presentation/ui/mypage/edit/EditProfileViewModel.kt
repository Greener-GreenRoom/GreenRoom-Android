package com.greener.presentation.ui.mypage.edit

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greener.domain.model.mypage.ImageUpdateFlag
import com.greener.domain.usecase.image.PickImageUseCase
import com.greener.domain.usecase.image.TakePictureUseCase
import com.greener.domain.usecase.mypage.EditUserProfileUseCase
import com.greener.presentation.model.UiState
import com.greener.presentation.model.mypage.UserSimpleInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val editUserProfileUseCase: EditUserProfileUseCase
) : ViewModel() {

    private val _userSimpleInfo = MutableStateFlow<UserSimpleInfo?>(null)
    val userSimpleInfo: StateFlow<UserSimpleInfo?> get() = _userSimpleInfo

    private val _profileImage = MutableStateFlow<String?>("")
    val profileImage: StateFlow<String?> get() = _profileImage

    private val _uiState = MutableStateFlow<UiState>(UiState.Empty)
    val uiState: StateFlow<UiState> get() = _uiState


    suspend fun editUserProfile(realPath: String?,nickName:String) {
        var path = realPath
        var imageUpdateFlag = ImageUpdateFlag.UPDATE.name

        if (realPath!!.startsWith("https:")) {
            imageUpdateFlag = ImageUpdateFlag.NONE.name
        } else if (realPath.isNullOrBlank()) {
            imageUpdateFlag = ImageUpdateFlag.REMOVE.name
        }

        val response = editUserProfileUseCase(nickName, path, imageUpdateFlag)
        if (response.isSuccess) {
            _uiState.update { UiState.Success }
        }
    }

    fun setUserSimpleInfo(userInfo: UserSimpleInfo) {
        _userSimpleInfo.update { userInfo }
    }

    fun getMyNickName(): String {
        if (_userSimpleInfo.value == null) {
            return ""
        }
        return _userSimpleInfo.value!!.nickName
    }

    fun getImage(pickImageUseCase: PickImageUseCase) {
        viewModelScope.launch {
            val result = pickImageUseCase()
            if (result.isSuccess) {
                val image = result.getOrThrow()
                if (image != "null") {
                    _profileImage.update { image }
                }

            } else {
                Log.d("확인", "실패")
                // todo 에러 처리
            }
        }
    }

    fun takePicture(takePictureUseCase: TakePictureUseCase) {
        viewModelScope.launch {
            val result = takePictureUseCase()
            if (result.isSuccess) {
                val image = result.getOrThrow()
                Log.d("확인", "takePicture 성공: $image")
                _profileImage.update { image }
            } else {
                Log.d("확인", "takePicture 실패")
            }
        }
    }

    fun setProfileImage(url: String? = null) {
        if (url == null) {
            _profileImage.update { "" }
            return
        }
        _profileImage.update { url }
    }
}