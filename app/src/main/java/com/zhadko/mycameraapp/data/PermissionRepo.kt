package com.zhadko.mycameraapp.data

interface PermissionRepo {

    fun allPermissionsGranted(): Boolean

}