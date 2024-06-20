package com.greener.presentation.ui.mypage.edit

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
    private val editUserProfileUseCase: EditUserProfileUseCase,
) : ViewModel() {

    private val _userSimpleInfo = MutableStateFlow<UserSimpleInfo?>(null)
    val userSimpleInfo: StateFlow<UserSimpleInfo?> get() = _userSimpleInfo

    private val _profileImage = MutableStateFlow<String?>(BLANK)
    val profileImage: StateFlow<String?> get() = _profileImage

    private val _uiState = MutableStateFlow<UiState>(UiState.Empty)
    val uiState: StateFlow<UiState> get() = _uiState

    suspend fun editUserProfile(realPath: String?, nickName: String) {
        var imageUpdateFlag = ImageUpdateFlag.UPDATE.name

        if (realPath!!.startsWith(START_HTTPS)) {
            imageUpdateFlag = ImageUpdateFlag.NONE.name
        } else if (realPath.isNullOrBlank()) {
            imageUpdateFlag = ImageUpdateFlag.REMOVE.name
        }

        val response = editUserProfileUseCase(nickName, realPath, imageUpdateFlag)
        if (response.isSuccess) {
            _uiState.update { UiState.Success }
        } else {
            _uiState.update { UiState.Error(response.exceptionOrNull()!!.message) }
            _uiState.update { UiState.Empty }
        }
    }

    fun setUserSimpleInfo(userInfo: UserSimpleInfo) {
        _userSimpleInfo.update { userInfo }
    }

    fun getMyNickName(): String {
        if (_userSimpleInfo.value == null) {
            return BLANK
        }
        return _userSimpleInfo.value!!.nickName
    }

    fun getImage(pickImageUseCase: PickImageUseCase) {
        viewModelScope.launch {
            val result = pickImageUseCase()
            if (result.isSuccess) {
                val image = result.getOrThrow()
                if (image != NULL) {
                    _profileImage.update { image }
                }
            } else {
                _profileImage.update { FAIL }
                _profileImage.update { BLANK }
            }
        }
    }

    fun takePicture(takePictureUseCase: TakePictureUseCase) {
        viewModelScope.launch {
            val result = takePictureUseCase()
            if (result.isSuccess) {
                val image = result.getOrThrow()
                _profileImage.update { image }
            } else {
                _profileImage.update { FAIL }
                _profileImage.update { BLANK }
            }
        }
    }

    fun setProfileImage(url: String? = null) {
        if (url == null) {
            _profileImage.update { BLANK }
            return
        }
        _profileImage.update { url }
    }

    companion object {
        const val FAIL = "fail"
        const val NULL = "null"
        const val BLANK = ""
        const val START_HTTPS = "https:"
    }
}
