package com.nalecy.osexperiment.process

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.nalecy.osexperiment.R

/**
 * @author nalecy
 * @since 2020/12/1
 */
class ProcessAdapter(layoutResId: Int, data: MutableList<Process>) :
    BaseQuickAdapter<Process, BaseViewHolder>(layoutResId, data) {
    override fun convert(holder: BaseViewHolder, item: Process) {
        holder.setText(R.id.process_name, item.name)
            .setText(R.id.process_priority, "进程优先级 " + item.priority)
            .setText(R.id.process_arrivalTime, "进程进入时间 " + item.arrivalTime)
            .setText(R.id.process_runTime, "需要的时间 " + item.needRunTime)
            .setText(R.id.process_useTime, "已运行时间 " + item.useTime)
            .setText(R.id.process_state,{
                when(item.state){
                    ProcessController.PROCESS_FINISH -> "已完成"
                    ProcessController.PROCESS_RUN -> "运行中"
                    ProcessController.PROCESS_WAIT -> "等待中"
                    else -> ""
                }
            }.invoke())
    }


}