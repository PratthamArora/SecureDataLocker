package com.pratthamarora.jetpacksecurity.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.pratthamarora.jetpacksecurity.R
import com.pratthamarora.jetpacksecurity.data.FileEntity
import com.pratthamarora.jetpacksecurity.ui.home.adapter.FileListAdapter
import com.pratthamarora.jetpacksecurity.util.Utility
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

class HomeFragment : Fragment() {

    private var fileListEntity = ArrayList<FileEntity>()
    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var fileListAdapter: FileListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        viewModel.getFileList()
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            if (it) {
                filesRecyclerView.isGone = true
                progressBar.isVisible = true
            } else {
                progressBar.isGone = true
                filesRecyclerView.isVisible = true
            }
        })

        viewModel.snackBarMsg.observe(viewLifecycleOwner, Observer {
            Utility.displaySnackBar(it, requireView())
        })

        viewModel.fileListEntity.observe(viewLifecycleOwner, Observer {
            fileListEntity = it
            setupRecyclerView()
        })
    }

    private fun setupRecyclerView() {
        fileListAdapter = FileListAdapter(fileListEntity)
        filesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = fileListAdapter
        }
    }
}