package com.nalecy.osexperiment.job

/**
 * @author nalecy
 * @since 2020/12/1
 */
class Job(// 作业名
    var name: String, // 提交时间
    var submitTime: Int, // 所需运行时间
    var runTime: Int, // 所需的资源：内存 + 磁带机
    var storage: Int, var machine: Int, // 作业状态：等待Wait、运行Run、结束Finish
    var state: Int
) {

    // 开始运行时刻
    var startTime = 0

    // 已用CPU时间
    var useTime = 0

    // 完成时刻 = 开始运行时刻 + 运行时间
    var endTime = 0

    // 周转时间 = 完成时刻 - 提交时间
    var turnTime = 0

    // 带权周转时间 = 周转时间 / 运行时间
    var weightTurnTime = 0f

}