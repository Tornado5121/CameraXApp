package com.zhadko.mycameraapp.ui.photocamera

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.Preview
import androidx.fragment.app.Fragment
import com.zhadko.mycameraapp.helpers.CameraHelper
import com.zhadko.mycameraapp.R
import com.zhadko.mycameraapp.databinding.PhotoCameraFragmentBinding
import com.zhadko.mycameraapp.ui.videocamera.VideoCameraFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhotoCameraFragment : Fragment() {

    private lateinit var binding: PhotoCameraFragmentBinding
    private val cameraViewModel by viewModel<PhotoCameraViewModel>()
    private val preview by lazy {
        Preview.Builder()
            .build()
            .also {
                it.setSurfaceProvider(binding.previewView.surfaceProvider)
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PhotoCameraFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.switchToVideoRegime.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, VideoCameraFragment())
                .commit()
        }

        if (cameraViewModel.isCameraPermissionGranted()) {
            cameraViewModel.startCamera(preview, viewLifecycleOwner)
        } else {
            cameraViewModel.askCameraPermission(requireActivity())
        }
        binding.takePhotoButton.setOnClickListener {
            cameraViewModel.takePhoto()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CameraHelper.REQUEST_CODE_PERMISSIONS) {
            if (cameraViewModel.isCameraPermissionGranted()) {
                cameraViewModel.startCamera(preview, viewLifecycleOwner)
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

}