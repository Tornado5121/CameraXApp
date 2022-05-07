package com.zhadko.mycameraapp.ui.photocamera

import android.app.Activity
import androidx.camera.core.Preview
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.zhadko.mycameraapp.data.PermissionRepo
import com.zhadko.mycameraapp.data.PhotoRepo
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class PhotoCameraViewModel(
    private val photoRepository: PhotoRepo,
    private val permissionRepository: PermissionRepo
) : ViewModel() {

    private var cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()

    fun startCamera(preview: Preview, lifecycleOwner: LifecycleOwner) {
        photoRepository.startCamera(preview, lifecycleOwner)
    }

    fun takePhoto() {
        photoRepository.takePhoto()
    }

    fun isCameraPermissionGranted() = permissionRepository.allPermissionsGranted()

    fun askCameraPermission(activity: Activity) {
        permissionRepository.askCameraPermission(activity)
    }

    override fun onCleared() {
        super.onCleared()
        cameraExecutor.shutdown()
    }
}