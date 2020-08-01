package com.pratthamarora.jetpacksecurity.ui.file

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.pratthamarora.jetpacksecurity.R
import com.pratthamarora.jetpacksecurity.util.Utility.displaySnackBar
import kotlinx.android.synthetic.main.fragment_file.*

class FileFragment : Fragment() {

    companion object {
        fun newInstance() = FileFragment()
    }

    private val viewModel by viewModels<FileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_file, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        fab_save_msg.setOnClickListener {
            if (!TextUtils.isEmpty(txtBody.text)) {
                viewModel.content.value = txtBody.text.toString()
                viewModel.storeFile()
                viewModel.storeEncryptedFile()
            } else {
                displaySnackBar("Message cannot be empty!", requireView())
            }
        }
    }

    private fun observeViewModel() {
        viewModel.snackBarMsg.observe(viewLifecycleOwner, Observer {
            displaySnackBar(it, requireView())
        })
    }

}