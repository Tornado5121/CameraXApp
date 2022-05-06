package com.zhadko.mycameraapp.ui.photocamera

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.zhadko.mycameraapp.CameraHelper
import com.zhadko.mycameraapp.R
import com.zhadko.mycameraapp.databinding.PhotoCameraFragmentBinding
import com.zhadko.mycameraapp.ui.videocamera.VideoCameraFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhotoCameraFragment : Fragment() {

    private lateinit var binding: PhotoCameraFragmentBinding
    private val cameraViewModel by viewModel<PhotoCameraViewModel>()

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
            startCamera()
        } else {
            askCameraPermission()
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

            CameraHelper.imageCapture = ImageCapture.Builder().build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    viewLifecycleOwner,
                    cameraSelector,
                    preview,
                    CameraHelper.imageCapture
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