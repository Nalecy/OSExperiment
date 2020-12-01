package com.nalecy.osexperiment.process

import com.nalecy.osexperiment.Constant.PROCESS_FINISH
import com.nalecy.osexperiment.Constant.PROCESS_RUN
import com.nalecy.osexperiment.Constant.PROCESS_WAIT
import com.nalecy.osexperiment.Constant.PROCESS_TYPE_MLFQ
import com.nalecy.osexperiment.Constant.PROCESS_TYPE_ROUND
import kotlin.math.min


/**
 * 进程管理类
 *
 * author: Lin
 * date: 2020/11/30
 */
class ProcessController(val type:Int) {
    // 所有进程
    var data: MutableList<Process> = mutableListOf()

    // 待运行进程
    private var mQueue: MutableList<Process> = mutableListOf()

    // 已完成队列
    private var finishQueue = mutableListOf<Process>()

    // 多级反馈队列
    private var mMultiQueue: MutableList<MutableList<Process>> = mutableListOf(
        mutableListOf(),
        mutableListOf(),
        mutableListOf()
    )

    // 总运行时间
    var runTime = 0

    // 上一个运行进程
    private var pre: Process? = null


    /**
     * 处理任务进队
     */
    private fun processEnqueue(queue: MutableList<Process>) {
        for (i in data.indices) {
            if (data[i].arrivalTime - 1 == runTime) {
                queue.add(data[i])
            }
        }
    }

    /**
     * 多级反馈队列运行时间片
     */
    private fun runMLFQ() {
        processEnqueue(mMultiQueue[0])
        if (mMultiQueue[0].size > 0) {
            runMultiMLFQ(0)
        } else if (mMultiQueue[1].size > 0) {
            runMultiMLFQ(1)
        } else if (mMultiQueue[2].size > 0) {
            runMultiMLFQ(2)
        }
        runTime++
    }

    private fun runMultiMLFQ(level: Int) {
        val queue = mMultiQueue[level]
        runToWait(level)
        // 时间片：1级队列1，2级队列2，3级队列4
        val timeSlice = 1 shl level
        val iterator = queue.iterator()
        if (iterator.hasNext()) {
            // 拿出当前的
            val runProcess = iterator.next()
            // 计算是否完成 并作出对应处理
            runProcess.run {
                useTime = min(useTime + timeSlice, needRunTime)
                if (useTime == needRunTime) {
                    state = PROCESS_FINISH
                    finishQueue.add(this)
                } else {
                    state = PROCESS_RUN
                    if (level < 2) {
                        mMultiQueue[level + 1].add(this)
                    }else{
                        mMultiQueue[level].add(this)
                    }
                }
            }
            iterator.remove()
        }
    }

    private fun runToWait(level: Int) {
        if (level == 0) {
            runToWait(mMultiQueue[1])
            runToWait(mMultiQueue[2])
        } else if (level == 1) {
            runToWait(mMultiQueue[2])
        }
    }

    private fun runToWait(queue: List<Process>) {
        for (process in queue) {
            process.state = PROCESS_WAIT
        }
    }

    fun run(){
        when(type){
            PROCESS_TYPE_MLFQ -> runMLFQ()
            PROCESS_TYPE_ROUND -> runRound()
        }
    }


    private fun runRound(){
        processEnqueue(mQueue)
        if (mQueue.isNotEmpty()){
            val runningProcess = mQueue.removeFirst()
            if (pre != null && pre?.state != PROCESS_FINISH){
                pre?.state = PROCESS_WAIT
            }
            runningProcess.run {
                useTime++
                state = if (useTime == needRunTime){
                    finishQueue.add(this)
                    PROCESS_FINISH
                }else{
                    mQueue.add(this)
                    PROCESS_RUN
                }
            }
            pre = runningProcess
        }
        runTime++
    }

    fun initData(data: MutableList<Process>) {
        reset(data)
    }

    fun reset(data: MutableList<Process>) {
        this.data.clear()
        this.data.addAll(data)
        mMultiQueue.forEach { it.clear() }
        mQueue.clear()
        finishQueue.clear()
        runTime = 0
    }

}