package com.nalecy.osexperiment.job

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nalecy.osexperiment.Constant
import com.nalecy.osexperiment.R
import com.nalecy.osexperiment.job.JobController.Companion.instance
import java.util.*

@SuppressLint("SetTextI18n")
class JobActivity : AppCompatActivity() {

    // 调度算法：FCFS/SJF/HRN
    private var mDispatchType = 0
    private lateinit var mTvStorage: TextView
    private lateinit var mTvMachine: TextView
    private lateinit var mRv: RecyclerView
    private lateinit var mTvTurnTime: TextView
    private lateinit var mTvWeightTurnTime: TextView
    private lateinit var mBtnNext: Button
    private lateinit var mTvTime: TextView
    private lateinit var mBtnReset: Button
    private lateinit var mAdapter: JobAdapter
    private var mData: MutableList<Job> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dispatch)
        instance!!.reset()
        initVariable()
        initView()
        initData()
    }

    private fun initVariable() {
        val intent = intent
        if (intent != null) {
            mDispatchType = intent.getIntExtra("dispatchType", Constant.JOB_FCFS)
            instance!!.dispatchType = mDispatchType
        }
        when (mDispatchType) {
            Constant.JOB_FCFS -> title = "FCFS"
            Constant.JOB_SJF -> title = "SJF"
            else -> {
            }
        }
    }

    private fun initView() {
        mTvStorage = findViewById(R.id.act_dispatch_tv_storage)
        mTvMachine = findViewById(R.id.act_dispatch_tv_machine)
        mRv = findViewById(R.id.act_dispatch_rv)
        mRv.layoutManager = LinearLayoutManager(this)
        mTvTurnTime = findViewById(R.id.act_dispatch_tv_turnTime)
        mTvWeightTurnTime = findViewById(R.id.act_dispatch_tv_weightTurnTime)
        mBtnNext = findViewById(R.id.act_dispatch_btn_next)
        mBtnReset = findViewById(R.id.act_dispatch_btn_reset)
        mTvTime = findViewById(R.id.act_dispatch_tv_time)
        mTvStorage.text = "主存空间：" + instance!!.storage
        mTvMachine.text = "磁带机：" + instance!!.machine
        mTvTime.text = "当前时间：" + 0
        mTvTurnTime.text = "平均周转时间："
        mTvWeightTurnTime.text = "带权平均周转时间："
        mBtnNext.setOnClickListener { clickNext() }
        mBtnReset.setOnClickListener { clickReset() }
    }

    private fun initData() {
        mData = LinkedList()
        mAdapter = JobAdapter(R.layout.item_job, mData)
        mRv.adapter = mAdapter
        instance!!.data = mData
        initJob()
    }

    private fun initJob() {
        val job1 = Job("1", 2, 5, 3, 2, Constant.JOB_WAIT)
        val job2 = Job("2", 1, 4, 2, 1, Constant.JOB_WAIT)
        val job3 = Job("3", 4, 6, 6, 3, Constant.JOB_WAIT)
        val job4 = Job("4", 10, 4, 2, 2, Constant.JOB_WAIT)
        val job5 = Job("5", 7, 5, 3, 1, Constant.JOB_WAIT)
        mData.add(job1)
        mData.add(job2)
        mData.add(job3)
        mData.add(job4)
        mData.add(job5)
    }

    private fun clickNext() {
        instance!!.run()
        mTvTime.text = "当前时间：" + instance!!.runTime
        mTvStorage.text = "主存空间：" + instance!!.storage
        mTvMachine.text = "磁带机：" + instance!!.machine
        showAverage()
        mAdapter.notifyDataSetChanged()
    }

    private fun clickReset() {
        instance!!.reset()
        mData.clear()
        initJob()
        mAdapter.notifyDataSetChanged()
        mTvStorage.text = "主存空间：" + instance!!.storage
        mTvMachine.text = "磁带机：" + instance!!.machine
        mTvTime.text = "当前时间：" + 0
        mTvTurnTime.text = "平均周转时间："
        mTvWeightTurnTime.text = "带权平均周转时间："
    }

    private fun showAverage() {
        if (jobFinish()) {
            var totalTurnTime = 0
            var totalWeightTurnTime = 0f
            for (job in mData) {
                totalTurnTime += job.turnTime
                totalWeightTurnTime += job.weightTurnTime
            }
            mTvTurnTime.text = "平均周转时间：" + totalTurnTime * 1.0 / mData.size
            mTvWeightTurnTime.text = "带权平均周转时间：" + totalWeightTurnTime / mData.size
        }
    }

    private fun jobFinish(): Boolean {
        var result = true
        for (job in mData) {
            if (job.state != Constant.JOB_FINISH) {
                result = false
                break
            }
        }
        return result
    }

    override fun onStart() {
        super.onStart()
    }
}