package com.nalecy.osexperiment.file

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.nalecy.osexperiment.Constant
import com.nalecy.osexperiment.R
import com.nalecy.osexperiment.databinding.ActivityFileMainBinding
import com.nalecy.osexperiment.job.JobActivity
import com.nalecy.osexperiment.process.Process
import com.nalecy.osexperiment.process.ProcessAdapter

class FileMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFileMainBinding
    private lateinit var adapter: FileUserAdapter

    private val usernameList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_file_main)
        usernameList.addAll(FileController.getUserSet())
        adapter = FileUserAdapter(R.layout.item_file_user,usernameList)
        binding.list.layoutManager = LinearLayoutManager(this)
        binding.list.adapter = adapter
        adapter.setOnItemClickListener { _, _, position ->
            startActivity(Intent(this, FileListActivity::class.java).apply {
                putExtra(FileListActivity.USERNAME_EXTRA, usernameList[position])
            })
        }
        binding.createUserBtn.setOnClickListener {
            if (FileController.createUser(binding.usernameEdt.text.toString())) {
                Toast.makeText(this, "创建成功",Toast.LENGTH_SHORT).show()
                usernameList.clear()
                usernameList.addAll(FileController.getUserSet())
                adapter.notifyDataSetChanged()
            }else{
                Toast.makeText(this, "创建失败",Toast.LENGTH_SHORT).show()
            }
        }
    }

    class FileUserAdapter(layoutResId: Int, data: MutableList<String>) :
        BaseQuickAdapter<String, BaseViewHolder>(layoutResId, data) {
        override fun convert(holder: BaseViewHolder, item: String) {
            holder.setText(R.id.item_file_username_tv,item)
        }

    }
}