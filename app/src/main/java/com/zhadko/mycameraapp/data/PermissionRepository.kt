package com.zhadko.mycameraapp.data

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.zhadko.mycameraapp.helpers.CameraHelper

class PermissionRepository(
    private val context: Context
) : PermissionRepo {

    override fun allPermissionsGranted() = CameraHelper.REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            context, it
        ) == PackageManager.PERMISSION_GRANTED
    }

}