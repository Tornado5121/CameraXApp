package com.zhadko.mycameraapp.ui.videocamera

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.Quality
import androidx.camera.video.QualitySelector
import androidx.camera.video.Recorder
import androidx.camera.video.VideoCapture
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.zhadko.mycameraapp.CameraHelper
import com.zhadko.mycameraapp.R
import com.zhadko.mycameraapp.databinding.VideoCameraFragmentBinding
import com.zhadko.mycameraapp.ui.photocamera.PhotoCameraFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class VideoCameraFragment : Fragment() {

    private lateinit var binding: VideoCameraFragmentBinding
    private val videoCameraViewModel by viewModel<VideoCameraViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = VideoCameraFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (videoCameraViewModel.isCameraPermissionGranted()) {
            startCamera()
        } else {
            askCameraPermission()
        }

        binding.switchToPhotoRegime.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .add(R.id.container, PhotoCameraFragment())
                .commit()
        }

        binding.videoCaptureButton.setOnClickListener {
            videoCameraViewModel.startRecordingVideo()
            if (videoCameraViewModel.isMyVideoRecording()) {
                binding.videoCaptureButton.apply {
                    text = getString(R.string.start_capture)
                }
            } else {
                binding.videoCaptureButton.apply {
                    text = getString(R.string.stop_capture)
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CameraHelper.REQUEST_CODE_PERMISSIONS) {
            if (videoCameraViewModel.isCameraPermissionGranted()) {
                startCamera()
            } else {
                Toast.makeText(
                    context,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
//                finish()
            }
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview =
                Preview.Builder()
                    .build()
                    .also {
                        it.setSurfaceProvider(binding.previewView.surfaceProvider)
                    }

            val recording = Recorder.Builder()
                .setQualitySelector(
                    QualitySelector
                        .from(Quality.HIGHEST)
                )
                .build()

            CameraHelper.videoCapture = VideoCapture.withOutput(recording)

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    viewLifecycleOwner,
                    cameraSelector,
                    preview,
                    CameraHelper.videoCapture
                )
            } catch (exc: Exception) {
                Log.e(CameraHelper.TAG, "Use case binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun askCameraPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            CameraHelper.REQUIRED_PERMISSIONS,
            CameraHelper.REQUEST_CODE_PERMISSIONS
        )
    }

}

//todo make preview work after accepting permission not after restarting the app
//todo refactor to MVVM
