package com.nalecy.osexperiment.job

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.nalecy.osexperiment.Constant
import com.nalecy.osexperiment.R

/**
 * @author nalecy
 * @since 2020/12/1
 */
class JobAdapter(layoutResId: Int, data: MutableList<Job>) :
    BaseQuickAdapter<Job, BaseViewHolder>(layoutResId, data) {
    override fun convert(baseViewHolder: BaseViewHolder, job: Job) {
        baseViewHolder.setText(R.id.item_job_tv_name, "编号 " + job.name)
            .setText(R.id.item_job_tv_submitTime, "进入时间 " + job.submitTime)
            .setText(R.id.item_job_tv_runTime, "所需运行时间 " + job.runTime)
            .setText(R.id.item_job_tv_storage, "内存需要 " + job.storage)
            .setText(R.id.item_job_tv_machine, "磁带机需要 " + job.machine)
            .setText(R.id.item_job_tv_startTime, "开始运行时刻 " + job.startTime)
            .setText(R.id.item_job_tv_endTime, "完成时刻 " + job.endTime)
//            .setText(R.id.item_job_tv_useTime, "完成 " + job.useTime)
            .setText(R.id.item_job_tv_turnTime, "周转时间 " + job.turnTime)
            .setText(R.id.item_job_tv_weightTurnTime, "带权周转时间 " + job.weightTurnTime)
            .setText(R.id.item_job_tv_state, run {
                when(job.state){
                    Constant.J_WAIT -> "后备状态"
                    Constant.J_RUN -> "执行状态"
                    Constant.J_FINISH -> "完成状态"
                    else -> ""
                }
            })
    }
}