package com.pratthamarora.jetpacksecurity.ui.sharedpref

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.pratthamarora.jetpacksecurity.R

class SharedPrefFragment : Fragment() {

    private lateinit var notificationsViewModel: SharedPrefViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProviders.of(this).get(SharedPrefViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_shared_pref, container, false)

        return root
    }
}