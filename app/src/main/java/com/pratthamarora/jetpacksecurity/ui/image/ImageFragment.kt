package com.pratthamarora.jetpacksecurity.ui.image

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.pratthamarora.jetpacksecurity.R
import com.pratthamarora.jetpacksecurity.util.Utility
import kotlinx.android.synthetic.main.fragment_image.*

class ImageFragment : Fragment() {
    companion object {
        private const val IMAGE_CODE = 1012
        private const val REQ_CODE = 1011
    }

    private val viewModel by viewModels<ImageViewModel>()
    private val permission = arrayOf(Manifest.permission.CAMERA)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()

        fb_capture_image.setOnClickListener {
            if (requestPermission()) {
                getImage()
            } else {
                ActivityCompat.requestPermissions(requireActivity(), permission, REQ_CODE)
            }
        }
    }

    private fun getImage() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            intent.resolveActivity(requireContext().packageManager)?.also {
                startActivityForResult(intent, IMAGE_CODE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val bmp = data?.extras?.get("data") as Bitmap
                viewModel.bitmap.value = bmp
                viewModel.saveEncryptedBitmap()
            }
        }
    }

    private fun requestPermission() = permission.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQ_CODE) {
            if (requestPermission()) {
                getImage()
            } else {
                viewModel.snackBarMsg.value = "Permission not granted!"
            }
        }
    }


    private fun observeViewModel() {
        viewModel.snackBarMsg.observe(viewLifecycleOwner, Observer {
            Utility.displaySnackBar(it, requireView())
        })

        viewModel.bitmap.observe(viewLifecycleOwner, Observer {
            imageView.setImageBitmap(it)
        })
    }
}