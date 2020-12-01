package com.nalecy.osexperiment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.databinding.DataBindingUtil
import com.nalecy.osexperiment.Constants.PROCESS_TYPE_MLFQ
import com.nalecy.osexperiment.Constants.PROCESS_TYPE_ROUND
import com.nalecy.osexperiment.Constants.STORAGE_TYPE_BEST
import com.nalecy.osexperiment.Constants.STORAGE_TYPE_FIRST
import com.nalecy.osexperiment.databinding.ActivityMainBinding
import com.nalecy.osexperiment.process.ProcessActivity
import com.nalecy.osexperiment.process.ProcessController
import com.nalecy.osexperiment.storage.StorageActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this,R.layout.activity_main)
        binding.processRound.setOnClickListener {
            startActivity(Intent(this,ProcessActivity::class.java).apply {
                putExtra("type",PROCESS_TYPE_ROUND)
            })
        }
        binding.processMlfq.setOnClickListener {
            startActivity(Intent(this,ProcessActivity::class.java).apply {
                putExtra("type",PROCESS_TYPE_MLFQ)
            })
        }
        binding.storageFirst.setOnClickListener {
            startActivity(Intent(this,StorageActivity::class.java).apply {
                putExtra("type",STORAGE_TYPE_FIRST)
            })
        }
        binding.storageBest.setOnClickListener {
            startActivity(Intent(this,StorageActivity::class.java).apply {
                putExtra("type",STORAGE_TYPE_BEST)
            })
        }
    }
}