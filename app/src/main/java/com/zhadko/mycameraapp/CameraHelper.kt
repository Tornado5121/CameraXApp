package com.zhadko.mycameraapp

import android.Manifest
import android.os.Build
import androidx.camera.core.ImageCapture
import androidx.camera.video.Recorder
import androidx.camera.video.VideoCapture

object CameraHelper {

    const val TAG = "CameraXApp"
    const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    const val REQUEST_CODE_PERMISSIONS = 10
    val REQUIRED_PERMISSIONS =
        mutableListOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
        ).apply {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }.toTypedArray()

    var imageCapture: ImageCapture? = null

    var videoCapture: VideoCapture<Recorder>? = null

}