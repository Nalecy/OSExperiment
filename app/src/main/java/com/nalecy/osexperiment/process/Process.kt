package com.nalecy.osexperiment.process

/**
 * @author nalecy
 * @since 2020/12/1
 */
class Process(
        /** 进程名字 */
        var name: String,
        /** 优先级 */
        var priority: Int,
        /** 存活时间 */
        var arrivalTime: Int,
        /** 运行时间 */
        var needRunTime: Int,
        /** 已用时间 */
        var useTime: Int,
        /** 进程状态 */
        var state: Int
)