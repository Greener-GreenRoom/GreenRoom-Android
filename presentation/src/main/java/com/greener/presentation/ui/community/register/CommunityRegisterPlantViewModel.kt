package com.greener.presentation.ui.community.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greener.domain.usecase.image.PickImageUseCase
import com.greener.domain.usecase.image.TakePictureUseCase
import com.greener.presentation.ui.mypage.edit.EditProfileViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class CommunityRegisterPlantViewModel : ViewModel() {

    private val _plantImage = MutableStateFlow<String?>(BLANK)
    val plantImage: StateFlow<String?> get() = _plantImage
    fun getImage(pickImageUseCase: PickImageUseCase) {
        viewModelScope.launch {
            val result = pickImageUseCase()
            if (result.isSuccess) {
                val image = result.getOrThrow()
                if (image != EditProfileViewModel.NULL) {
                    _plantImage.update { image }
                }
            } else {
                _plantImage.update { FAIL }
                _plantImage.update { BLANK }
            }
        }
    }

    fun takePicture(takePictureUseCase: TakePictureUseCase) {
        viewModelScope.launch {
            val result = takePictureUseCase()
            if (result.isSuccess) {
                val image = result.getOrThrow()
                _plantImage.update { image }
            } else {
                _plantImage.update { FAIL }
                _plantImage.update { BLANK }
            }
        }
    }

    companion object {
        const val FAIL = "fail"
        const val BLANK = ""
    }
}