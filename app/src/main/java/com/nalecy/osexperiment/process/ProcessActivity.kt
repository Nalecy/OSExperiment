package com.nalecy.osexperiment.process

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.nalecy.osexperiment.Constant.PROCESS_TYPE_MLFQ
import com.nalecy.osexperiment.Constant.PROCESS_TYPE_ROUND
import com.nalecy.osexperiment.R
import com.nalecy.osexperiment.databinding.ActivityProcessBinding

/**
 * @author nalecy
 * @since 2020/12/1
 */
class ProcessActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProcessBinding
    private lateinit var adapter: ProcessAdapter
    private var type: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        type = intent.getIntExtra("type",PROCESS_TYPE_ROUND)

        val controller = ProcessController(type)
        controller.initData(ProcessFactory.getData())
        binding = DataBindingUtil.setContentView(this,R.layout.activity_process)
        adapter = ProcessAdapter(R.layout.item_process,controller.data)
        binding.list.layoutManager = LinearLayoutManager(this)
        binding.list.adapter = adapter
        binding.title.text = when(type){
            PROCESS_TYPE_ROUND -> "轮转"
            PROCESS_TYPE_MLFQ -> "多级反馈"
            else -> ""
        }
        binding.processRunBtn.setOnClickListener {
            controller.run()
            adapter.notifyDataSetChanged()
        }
        binding.processResetBtn.setOnClickListener {
            controller.reset(ProcessFactory.getData())
            adapter.notifyDataSetChanged()
        }
    }
}