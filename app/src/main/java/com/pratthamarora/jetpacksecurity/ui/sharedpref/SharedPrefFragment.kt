package com.pratthamarora.jetpacksecurity.ui.sharedpref

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.pratthamarora.jetpacksecurity.R
import kotlinx.android.synthetic.main.fragment_shared_pref.*

class SharedPrefFragment : Fragment() {

    private val viewModel by viewModels<SharedPrefViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_shared_pref, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        viewModel.apply {
            getUserName()
            getUserEmail()
        }
        fab_save_pref.setOnClickListener {
            if (!TextUtils.isEmpty(txtUserId.text) && !TextUtils.isEmpty(txtEmail.text)) {
                viewModel.userName.value = txtUserId.text.toString()
                viewModel.userEmail.value = txtEmail.text.toString()
                viewModel.saveUserData()
            } else {
                Snackbar.make(requireView(), "Please fill all the fields!", Snackbar.LENGTH_LONG)
                    .show()
            }

        }
    }

    private fun observeViewModel() {
        viewModel.userName.observe(viewLifecycleOwner, Observer {
            txtUserId.setText(it)
        })

        viewModel.userEmail.observe(viewLifecycleOwner, Observer {
            txtEmail.setText(it)
        })

        viewModel.snackBarMsg.observe(viewLifecycleOwner, Observer {
            Snackbar.make(requireView(), it, Snackbar.LENGTH_LONG).show()
        })

    }
}