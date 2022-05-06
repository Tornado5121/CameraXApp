package com.zhadko.mycameraapp.ui.photocamera

import androidx.lifecycle.ViewModel
import com.zhadko.mycameraapp.data.PermissionRepository
import com.zhadko.mycameraapp.data.PhotoRepository
import com.zhadko.mycameraapp.data.VideoRepository
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class PhotoCameraViewModel(
    private val photoRepository: PhotoRepository,
    private val permissionRepository: PermissionRepository
) : ViewModel() {

    private var cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()

    fun takePhoto() {
        photoRepository.takePhoto()
    }

    fun isCameraPermissionGranted() = permissionRepository.allPermissionsGranted()

//    fun askCameraPermission() {
//        permissionRepository.askCameraPermission()
//    }

    override fun onCleared() {
        super.onCleared()
        cameraExecutor.shutdown()
    }
}