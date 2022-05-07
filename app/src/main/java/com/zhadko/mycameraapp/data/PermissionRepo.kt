package com.zhadko.mycameraapp.data

import android.app.Activity

interface PermissionRepo {

    fun allPermissionsGranted(): Boolean
    fun askCameraPermission(activity: Activity)

}