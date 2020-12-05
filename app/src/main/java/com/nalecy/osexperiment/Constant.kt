package com.nalecy.osexperiment

/**
 * @author nalecy
 * @since 2020/12/1
 */
object Constant {
    const val PROCESS_FINISH = 1
    const val PROCESS_WAIT = 2
    const val PROCESS_RUN = 3

    const val PROCESS_TYPE_ROUND = 0
    const val PROCESS_TYPE_MLFQ = 1

    const val STORAGE_TYPE_FIRST = 1
    const val STORAGE_TYPE_BEST = 2


    const val JOB_WAIT = 1
    const val JOB_RUN = 2
    const val JOB_FINISH = 3
    
    const val JOB_FCFS = 111
    const val JOB_SJF = 222

    const val F_STATE_OPENING = 10
    const val F_STATE_WAIT = 20
    const val F_STATE_READ = 30
    const val F_STATE_WRITE = 40
    const val F_STATE_DELETE = 50
    
    const val F_PROTECT_READ = 0b001
    const val F_PROTECT_WRITE = 0b010
    const val F_PROTECT_RUN = 0b100
}