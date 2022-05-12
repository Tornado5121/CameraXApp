package com.zhadko.mycameraapp.ui.videocamera

import android.app.Activity
import androidx.camera.core.Preview
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.zhadko.mycameraapp.data.PermissionRepo
import com.zhadko.mycameraapp.data.VideoRepo

class VideoCameraViewModel(
    private val permissionRepository: PermissionRepo,
    private val videoRepository: VideoRepo
) : ViewModel() {

    fun isCameraPermissionGranted() = permissionRepository.allPermissionsGranted()

    fun startRecordingVideo() {
        videoRepository.captureVideo()
    }

    fun isMyVideoRecording() : Boolean {
        return videoRepository.isMyVideoRecording()
    }

    fun startVideoCamera(preview: Preview, lifecycleOwner: LifecycleOwner) {
        videoRepository.startCamera(preview, lifecycleOwner)
    }

}