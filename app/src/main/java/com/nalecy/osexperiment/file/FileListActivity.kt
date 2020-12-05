package com.nalecy.osexperiment.file

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.nalecy.osexperiment.Constant
import com.nalecy.osexperiment.R
import com.nalecy.osexperiment.databinding.ActivityFileListBinding

class FileListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFileListBinding

    private lateinit var adapter: FileUserFileAdapter

    private val callback: (Boolean, String) -> Unit = { success, msg ->
        Toast.makeText(this, msg,Toast.LENGTH_SHORT).show()
        if (success){
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!FileController.setCurUser(intent.getStringExtra(USERNAME_EXTRA))) {
            Toast.makeText(this, "无该用户", Toast.LENGTH_SHORT).show()
            finish()
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_file_list)
        adapter = FileUserFileAdapter(R.layout.item_file_userfile, FileController.getCurFileList())
        FileController.addCallback(callback)
        binding.list.layoutManager = LinearLayoutManager(this)
        binding.list.adapter = adapter
        initBtnClickListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        FileController.removeCallback(callback)
    }

    private fun initBtnClickListener() {
        binding.createBtn.setOnClickListener {
            FileController.createFile(binding.fileNameEdt.text.toString())
        }
        binding.deleteBtn.setOnClickListener {
            FileController.deleteFile(binding.fileNameEdt.text.toString())
        }
        binding.openBtn.setOnClickListener {
            FileController.openFile(binding.fileNameEdt.text.toString())
        }
        binding.readBtn.setOnClickListener {
            FileController.readFile(binding.fileNameEdt.text.toString())
        }
        binding.writeBtn.setOnClickListener {
            FileController.writeFile(binding.fileNameEdt.text.toString())
        }
        binding.closeBtn.setOnClickListener {
            FileController.closeFile(binding.fileNameEdt.text.toString())
        }
    }



    class FileUserFileAdapter(layoutResId: Int, data: MutableList<UserFile>) :
        BaseQuickAdapter<UserFile, BaseViewHolder>(layoutResId, data) {
        override fun convert(holder: BaseViewHolder, item: UserFile) {
            holder.setText(R.id.item_file_filename, "文件名：${item.fileName}")
                .setText(R.id.item_file_filesize, "文件大小：${item.fileSize}")
                .setText(R.id.item_file_filestate, run {
                    when (item.state) {
                        Constant.F_STATE_OPENING -> "执行中"
                        Constant.F_STATE_READ -> "读取中"
                        Constant.F_STATE_WAIT -> "空闲中"
                        Constant.F_STATE_WRITE -> "写入中"
                        Constant.F_STATE_DELETE -> "删除中"
                        else -> "异常"
                    }
                })
                .setText(R.id.item_file_protect, run {
                    var s = "文件权限："
                    if (item.canRun()) s += "可执行 "
                    if (item.canRead()) s += "可读 "
                    if (item.canWrite()) s += "可写 "
                    s
                })
        }
    }

    companion object {
        const val USERNAME_EXTRA = "cur_username"

    }
}