package com.zhadko.mycameraapp.data

interface VideoRepo: Camera {

    fun captureVideo()
    fun isMyVideoRecording(): Boolean

}