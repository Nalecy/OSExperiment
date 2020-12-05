package com.nalecy.osexperiment.file

import android.text.SpannableStringBuilder
import com.nalecy.osexperiment.Constant
import com.nalecy.osexperiment.Constant.F_STATE_WAIT

/**
 * @author nalecy
 * @since 2020/12/1
 */
class UserFile(
    var fileName: String,
    var protectCode: Int = 0b000,
    var fileSize: Int = 0,
    var state: Int = F_STATE_WAIT
) {

    fun canRead() = protectCode.and(Constant.F_PROTECT_READ) == Constant.F_PROTECT_READ

    fun canWrite() = protectCode.and(Constant.F_PROTECT_WRITE) == Constant.F_PROTECT_WRITE

    fun canRun() = protectCode.and(Constant.F_PROTECT_RUN) == Constant.F_PROTECT_RUN

    override fun equals(other: Any?): Boolean {
        return other is UserFile && other.fileName == fileName
    }

    override fun hashCode(): Int {
        return fileName.hashCode()
    }
}