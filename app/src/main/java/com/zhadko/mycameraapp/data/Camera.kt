package com.zhadko.mycameraapp.data

import androidx.camera.core.Preview
import androidx.lifecycle.LifecycleOwner

interface Camera {

    fun startCamera(preview: Preview, lifecycleOwner: LifecycleOwner)

}