package com.zhadko.mycameraapp.data

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.zhadko.mycameraapp.CameraHelper

class PermissionRepository(
    private val context: Context
) {

    fun allPermissionsGranted() = CameraHelper.REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            context, it
        ) == PackageManager.PERMISSION_GRANTED
    }

}