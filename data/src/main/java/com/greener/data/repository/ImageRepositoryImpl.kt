package com.greener.data.repository

import android.Manifest
import android.content.Context
import android.os.Build
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.greener.data.R
import com.greener.domain.model.exception.RequestDeniedException
import com.greener.domain.repository.ImageRepository
import com.gun0912.tedpermission.TedPermissionResult
import com.gun0912.tedpermission.coroutine.TedPermission
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    @ActivityContext private val context: Context,
): ImageRepository {
    private val imageEvent = MutableSharedFlow<String>()
    private val pickImageResult = (context as AppCompatActivity).registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        CoroutineScope(Dispatchers.Default).launch {
            imageEvent.emit(uri.toString())
        }
    }

    override suspend fun pickImage(): Result<String> {
        val result = getReadPermissionResult()

        return if (result.isGranted) {
            pickImageResult.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            Result.success(imageEvent.first())
        } else {
            Result.failure(RequestDeniedException(result.deniedPermissions.first()))
        }
    }

    override suspend fun takePicture(): Unit  {

    }


    private suspend fun getReadPermissionResult(): TedPermissionResult {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }
        return TedPermission.create()
            .setDeniedMessage(R.string.read_permission_denied)
            .setPermissions(permission)
            .check()
    }

    private suspend fun getCameraPermissionResult(): TedPermissionResult =
        TedPermission.create()
            .setDeniedMessage(R.string.camera_permission_denied)
            .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
            .check()

    companion object {
        const val REQ_IMAGE_CAPTURE = 300
    }
}
