package com.zhadko.mycameraapp.ui.videocamera

import androidx.lifecycle.ViewModel
import com.zhadko.mycameraapp.data.PermissionRepository
import com.zhadko.mycameraapp.data.VideoRepository

class VideoCameraViewModel(
    private val permissionRepository: PermissionRepository,
    private val videoRepository: VideoRepository
) : ViewModel() {

    fun isCameraPermissionGranted() = permissionRepository.allPermissionsGranted()

    fun startRecordingVideo() {
        videoRepository.captureVideo()
    }

    fun isMyVideoRecording() : Boolean {
        return videoRepository.isMyVideoRecording()
    }

}