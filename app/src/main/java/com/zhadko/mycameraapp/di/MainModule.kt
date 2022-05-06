package com.zhadko.mycameraapp.di

import com.zhadko.mycameraapp.data.PermissionRepository
import com.zhadko.mycameraapp.data.PhotoRepository
import com.zhadko.mycameraapp.data.VideoRepository
import com.zhadko.mycameraapp.ui.photocamera.PhotoCameraViewModel
import com.zhadko.mycameraapp.ui.videocamera.VideoCameraViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {
    single { VideoRepository(androidContext()) }
    single { PhotoRepository(androidContext()) }
    single { PermissionRepository(androidContext()) }
}

val viewModelModule = module {
    viewModel { PhotoCameraViewModel(get(), get()) }
    viewModel { VideoCameraViewModel(get(), get()) }
}