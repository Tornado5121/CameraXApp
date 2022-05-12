package com.zhadko.mycameraapp.ui.videocamera

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.Preview
import androidx.fragment.app.Fragment
import com.zhadko.mycameraapp.helpers.CameraHelper
import com.zhadko.mycameraapp.R
import com.zhadko.mycameraapp.databinding.VideoCameraFragmentBinding
import com.zhadko.mycameraapp.ui.photocamera.PhotoCameraFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class VideoCameraFragment : Fragment() {

    private lateinit var binding: VideoCameraFragmentBinding
    private val videoCameraViewModel by viewModel<VideoCameraViewModel>()
    private val preview by lazy {
        Preview.Builder()
            .build()
            .also {
                it.setSurfaceProvider(binding.previewView.surfaceProvider)
            }
    }

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
            videoCameraViewModel.startVideoCamera(preview, viewLifecycleOwner)
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
                videoCameraViewModel.startVideoCamera(preview, viewLifecycleOwner)
            } else {
                Toast.makeText(
                    context,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun askCameraPermission() {
        requestPermissions(
            CameraHelper.REQUIRED_PERMISSIONS,
            CameraHelper.REQUEST_CODE_PERMISSIONS
        )
    }

}
