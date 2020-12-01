package com.nalecy.osexperiment.process

import com.nalecy.osexperiment.Constant

/**
 * @author nalecy
 * @since 2020/12/1
 */
object ProcessFactory {

    fun getData() = mutableListOf<Process>().apply {
        for (i in 1..5) {
            // 优先数设置为1, 3, 5
            val process = Process(
                "Process $i",
                if (i and 1 == 1) i else i - 1,
                i,
                5,
                0,
                Constant.PROCESS_WAIT
            )
            add(process)
        }
        get(2).arrivalTime = 7
        get(3).arrivalTime = 3
    }



}