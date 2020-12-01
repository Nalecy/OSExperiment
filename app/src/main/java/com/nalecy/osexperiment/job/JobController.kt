package com.nalecy.osexperiment.job

import com.nalecy.osexperiment.Constant
import java.util.*

/**
 * @author nalecy
 * @since 2020/12/1
 */
internal class JobController private constructor() {

    var data: List<Job>? = null

    /**
     * 等待队列
     */
    private val waitQueue: MutableList<Job>

    /**
     * 运行作业队列
     */
    private val runningQueue: MutableList<Job>

    /**
     * 总运行时间
     */
    var runTime = 0

    /**
     * 当前可用空间
     */
    var storage: Int

    /**
     * 当前可用磁带机
     */
    var machine: Int
    
    var dispatchType = 0
    fun run() {
        when (dispatchType) {
            Constant.J_FCFS -> runFCFS()
            Constant.J_SJF -> runSJF()
        }
    }

    private fun runFCFS() {
        runTime++
        jobEnqueue(runningQueue)
        if (runningQueue.size > 0) {
            val job = runningQueue[0]
            job.state = Constant.J_RUN
            // 设置开始时间，占用资源
            if (job.startTime == 0) {
                job.startTime = runTime
                storage -= job.storage
                machine -= job.machine
            }
            job.useTime = job.useTime + 1
            if (job.useTime == job.runTime) {
                job.endTime = runTime
                job.turnTime = runTime - job.submitTime
                job.weightTurnTime = job.turnTime.toFloat() / job.runTime
                runningQueue.remove(job)
                job.state = Constant.J_FINISH
                // 归还资源
                storage += job.storage
                machine += job.machine
            }
        }
    }

    /**
     * SJF
     */
    private fun runSJF() {
        runTime++
        // 先将到达任务加入等待队列
        jobEnqueue(waitQueue)
        // 最短作业优先
        waitQueue.sortWith { job1: Job, job2: Job -> job1.runTime - job2.runTime }
        // 若当前没有任务执行，从等待队列中拿出最短作业
        if (runningQueue.size == 0 && waitQueue.size > 0) {
            runningQueue.add(waitQueue.removeAt(0))
        }
        if (runningQueue.size > 0) {
            val job = runningQueue[0]
            job.state = Constant.J_RUN
            // 设置开始时间，占用资源
            if (job.startTime == 0) {
                job.startTime = runTime
                storage -= job.storage
                machine -= job.machine
            }
            job.useTime = job.useTime + 1
            if (job.useTime == job.runTime) {
                job.endTime = runTime
                job.turnTime = runTime - job.submitTime
                job.weightTurnTime = job.turnTime.toFloat() / job.runTime
                runningQueue.remove(job)
                job.state = Constant.J_FINISH
                // 归还资源
                storage += job.storage
                machine += job.machine
            }
        }
    }

    private fun jobEnqueue(queue: MutableList<Job>) {
        for (job in data!!) {
            if (job.submitTime == runTime) {
                queue.add(job)
            }
        }
    }

    fun reset() {
        storage = STORAGE_SIZE
        machine = MACHINE_SIZE
        waitQueue.clear()
        runningQueue.clear()
        runTime = 0
    }

    companion object {

        // 初始可用空间10KB
        private const val STORAGE_SIZE = 10

        // 初始磁带机5台
        private const val MACHINE_SIZE = 5

        @Volatile
        private var INSTANCE: JobController? = null
        @JvmStatic
        val instance: JobController?
            get() {
                if (INSTANCE == null) {
                    synchronized(JobController::class.java) {
                        if (INSTANCE == null) {
                            INSTANCE = JobController()
                        }
                    }
                }
                return INSTANCE
            }
    }

    init {
        storage = STORAGE_SIZE
        machine = MACHINE_SIZE
        waitQueue = LinkedList()
        runningQueue = LinkedList()
    }
}