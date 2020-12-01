package com.nalecy.osexperiment.storage

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.nalecy.osexperiment.R
import com.nalecy.osexperiment.process.Process

/**
 * @author nalecy
 * @since 2020/12/1
 */
class SpaceAdapter(layoutResId: Int, data: MutableList<Space>) :
    BaseQuickAdapter<Space, BaseViewHolder>(layoutResId, data) {

    override fun convert(holder: BaseViewHolder, item: Space) {
        holder.setText(R.id.space_start, "开始地址 ${item.startAddress}")
            .setText(R.id.space_size, "长度 ${item.size}")
            .setText(R.id.space_free, {
                if (item.free)
                    "空闲"
                else
                    "占用"
            }.invoke())
    }
}
