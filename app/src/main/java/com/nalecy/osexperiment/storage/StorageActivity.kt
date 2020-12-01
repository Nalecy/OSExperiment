package com.nalecy.osexperiment.storage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nalecy.osexperiment.Constants
import com.nalecy.osexperiment.Constants.STORAGE_TYPE_BEST
import com.nalecy.osexperiment.Constants.STORAGE_TYPE_FIRST
import com.nalecy.osexperiment.R
import com.nalecy.osexperiment.databinding.ActivityStorageBinding
import com.nalecy.osexperiment.process.ProcessController

class StorageActivity : AppCompatActivity() {
    private var type: Int = -1
    private lateinit var binding: ActivityStorageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        type = intent.getIntExtra("type",STORAGE_TYPE_FIRST)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_storage)
        binding.title.text = when(type){
            STORAGE_TYPE_FIRST -> "首次适应算法"
            STORAGE_TYPE_BEST -> "最佳适应算法"
            else -> ""
        }
    }
}