package com.nalecy.osexperiment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.databinding.DataBindingUtil
import com.nalecy.osexperiment.databinding.ActivityMainBinding
import com.nalecy.osexperiment.process.ProcessActivity
import com.nalecy.osexperiment.process.ProcessController

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this,R.layout.activity_main)
        binding.processRound.setOnClickListener {
            startActivity(Intent(this,ProcessActivity::class.java).apply {
                putExtra("type",ProcessController.TYPE_ROUND)
            })
        }
        binding.processMlfq.setOnClickListener {
            startActivity(Intent(this,ProcessActivity::class.java).apply {
                putExtra("type",ProcessController.TYPE_MLFQ)
            })
        }
    }
}