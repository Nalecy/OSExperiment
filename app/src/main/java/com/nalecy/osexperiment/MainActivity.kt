package com.nalecy.osexperiment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nalecy.osexperiment.Constant.JOB_FCFS
import com.nalecy.osexperiment.Constant.JOB_SJF
import com.nalecy.osexperiment.Constant.PROCESS_TYPE_MLFQ
import com.nalecy.osexperiment.Constant.PROCESS_TYPE_ROUND
import com.nalecy.osexperiment.Constant.STORAGE_TYPE_BEST
import com.nalecy.osexperiment.Constant.STORAGE_TYPE_FIRST
import com.nalecy.osexperiment.databinding.ActivityMainBinding
import com.nalecy.osexperiment.file.FileMainActivity
import com.nalecy.osexperiment.job.JobActivity
import com.nalecy.osexperiment.process.ProcessActivity
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
        binding.jobFcfs.setOnClickListener {
            startActivity(Intent(this,JobActivity::class.java).apply {
                putExtra("dispatchType",JOB_FCFS)
            })
        }
        binding.jobSjf.setOnClickListener {
            startActivity(Intent(this,JobActivity::class.java).apply {
                putExtra("dispatchType",JOB_SJF)
            })
        }
        binding.file.setOnClickListener {
            startActivity(Intent(this,FileMainActivity::class.java))
        }

    }
}