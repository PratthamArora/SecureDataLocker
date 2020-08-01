package com.pratthamarora.jetpacksecurity.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pratthamarora.jetpacksecurity.R
import com.pratthamarora.jetpacksecurity.data.FileEntity
import kotlinx.android.synthetic.main.secure_file_list_view.view.*

class FileListAdapter(
    private val fileList: ArrayList<FileEntity>
) : RecyclerView.Adapter<FileListAdapter.FileListViewHolder>() {

    class FileListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var data: FileEntity

        fun onBind(file: FileEntity) {
            data = file

            itemView.apply {
                txtFileName.text = data.fileName
                txtFile.text = data.file.toString()
                txtFileSize.text = data.fileSize
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileListViewHolder {
        return FileListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.secure_file_list_view, parent, false)
        )
    }

    override fun getItemCount(): Int = fileList.size

    override fun onBindViewHolder(holder: FileListViewHolder, position: Int) {
        val file = fileList[position]
        holder.onBind(file)
    }
}