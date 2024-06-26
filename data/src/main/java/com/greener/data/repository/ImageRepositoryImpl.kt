package com.greener.data.repository

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
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
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    @ActivityContext private val context: Context,
) : ImageRepository {
    private val imageEvent = MutableSharedFlow<String>()
    private val pickImageResult = (context as AppCompatActivity).registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        CoroutineScope(Dispatchers.Default).launch {
            imageEvent.emit(uri.toString())
        }
    }

    private val getTakePicturePreview = (context as AppCompatActivity).registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
        CoroutineScope(Dispatchers.Default).launch {
            it?.let { it1 -> bitmapToUri(it1).toString() }?.let { it2 -> imageEvent.emit(it2) }
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

    override suspend fun takePicture(): Result<String> {
        val result = getCameraPermissionResult()

        return if (result.isGranted) {
            getTakePicturePreview.launch(null)
            Result.success(imageEvent.first())
        } else {
            Result.failure(RequestDeniedException(result.deniedPermissions.first()))
        }
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
            .setPermissions(Manifest.permission.CAMERA)
            .check()

    private fun bitmapToUri(bitmap: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)

        val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "", null)
        return Uri.parse(path)
    }
}
