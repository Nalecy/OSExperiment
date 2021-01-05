package com.nalecy.osexperiment.file

import android.os.Handler
import android.os.Looper
import com.nalecy.osexperiment.Constant
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * @author nalecy
 * @since 2020/12/1
 */
object FileController {
    private const val OPERATING_TIME = 3000L

    private val masterFileDir = mutableMapOf<String, MutableList<UserFile>>()

    var curUserName = ""

    fun createUser(userName: String): Boolean {
        if (masterFileDir.size >= 10 || masterFileDir.keys.contains(userName)) return false
        masterFileDir[userName] = mutableListOf()
        return true
    }

    fun setCurUser(userName: String?): Boolean {
        if (!masterFileDir.keys.contains(userName)) return false
        curUserName = userName!!
        return true
    }

    fun createFile(fileName: String) {
        val file = randomProtectCode(UserFile(fileName = fileName, fileSize =  randomSize()))
        when {
            masterFileDir[curUserName]!!.contains(file) -> {
                callback(false,"存在该文件")
            }
            masterFileDir[curUserName]!!.size == 10 -> {
                callback(false,"文件满了")
            }
            else -> {
                masterFileDir[curUserName]?.add(file)
                callback(true,"创建成功")
            }
        }
    }

    private fun randomProtectCode(userFile: UserFile)  = userFile.apply {
        protectCode = protectCode.or(Constant.F_PROTECT_RUN)
        if (Math.random() > 0.3){
            protectCode = protectCode.or(Constant.F_PROTECT_WRITE)
        }
        if (Math.random() > 0.3){
            protectCode = protectCode.or(Constant.F_PROTECT_READ)
        }
    }

    private fun randomSize() = (Math.random() * 200).toInt()

    private fun findFile(fileName: String): UserFile? = run {
        masterFileDir[curUserName]?.forEach {
            if (it.fileName == fileName) {
                return@run it
            }
        }
        null
    }

    fun deleteFile(fileName: String) {
        executor.submit {
            val delete: UserFile? = findFile(fileName)
            delete?.let {
                // 可写的,有空的，才能被删除
                if (!it.canWrite() || !checkIsIdle(it)) {
                    callback(false, "文件不可用")
                    return@let
                }
                it.state = Constant.F_STATE_DELETE
                callback(true, "删除文件中")
                morkOperating()
                masterFileDir[curUserName]?.remove(it)
                callback(true, "删除文件成功")
            } ?: run {
                callback(false, "找不到该文件")
            }
        }
    }

    private fun checkIsIdle(file: UserFile) = file.state == Constant.F_STATE_WAIT

    private var openFileNum = 0

    fun openFile(fileName: String) {
        executor.submit {
            val open: UserFile? = findFile(fileName)
            open?.let {
                // 可运行的,有空的，才能被打开
                if (!it.canRun() || !checkIsIdle(it)) {
                    callback(false, "文件不可用")
                    return@let
                }
                if (openFileNum > 4) {
                    callback(false, "运行太多文件了")
                    return@let
                }
                it.state = Constant.F_STATE_OPENING
                openFileNum++
                callback(true, "打开文件成功")
            } ?: run {
                callback(false, "找不到该文件")
            }
        }
    }

    fun closeFile(fileName: String) {
        executor.submit {
            val open: UserFile? = findFile(fileName)
            open?.let {
                // 可读的,有空的，才能被打开
                if (!it.canRun() || !(it.state == Constant.F_STATE_OPENING)) {
                    callback(false, "无法关闭文件")
                    return@let
                }
                it.state = Constant.F_STATE_WAIT
                openFileNum--
                callback(true, "关闭文件成功")
            } ?: run {
                callback(false, "找不到该文件")
            }
        }
    }

    fun writeFile(fileName: String) {
        executor.submit {
            val write: UserFile? = findFile(fileName)
            write?.let {
                // 可读的,有空的，才能被打开
                if (!it.canWrite() || !checkIsIdle(it)) {
                    callback(false, "文件不可用")
                    return@let
                }
                it.state = Constant.F_STATE_WRITE
                callback(true, "写入文件中")
                morkOperating()
                it.state = Constant.F_STATE_WAIT
                it.fileContent += "许怀鑫"
                callback(true, "写入\"许怀鑫\"成功")
            } ?: run {
                callback(false, "找不到该文件")
            }
        }
    }

    fun readFile(fileName: String) {
        executor.submit {
            val read: UserFile? = findFile(fileName)
            read?.let {
                // 可读的,有空的，才能被打开
                if (!it.canRead() || !checkIsIdle(it)) {
                    callback(false, "文件不可用")
                    return@let
                }
                it.state = Constant.F_STATE_READ
                callback(true, "读取文件中")
                morkOperating()
                it.state = Constant.F_STATE_WAIT
                callback(true, "读取成功,文件内容：${it.fileContent}")
            } ?: run {
                callback(false, "找不到该文件")
            }
        }
    }

    fun getCurFileList(): MutableList<UserFile> = masterFileDir[curUserName]!!

    fun getUserSet() = masterFileDir.keys

    fun addCallback(callback: (Boolean, String) -> Unit) {
        callbacks.add(callback)
    }

    fun removeCallback(callback: (Boolean, String) -> Unit) {
        callbacks.remove(callback)
    }

    private fun callback(success: Boolean, msg: String) {
        mainHandler.post{
            callbacks.forEach {
                it.invoke(success, msg)
            }
        }
    }

    private fun morkOperating() {
        Thread.sleep(OPERATING_TIME)
    }

    private val callbacks = mutableListOf<((Boolean, String) -> Unit)>()

    private val executor = Executors.newSingleThreadExecutor()

    private val mainHandler = Handler(Looper.getMainLooper())

    //初始化数据
    init {
        createUser("小许")
        createUser("小怀")
        createUser("小鑫")
        setCurUser("小许")
        for (i in 1..9) {
            createFile("$i")
        }
        masterFileDir["小许"]?.get(0)?.protectCode = 0b001
        masterFileDir["小许"]?.get(1)?.protectCode = 0b010
        masterFileDir["小许"]?.get(2)?.protectCode = 0b100
        masterFileDir["小许"]?.get(3)?.protectCode = 0b011
        masterFileDir["小许"]?.get(4)?.protectCode = 0b110
        masterFileDir["小许"]?.get(5)?.protectCode = 0b101
        masterFileDir["小许"]?.get(6)?.protectCode = 0b111
        masterFileDir["小许"]?.get(7)?.protectCode = 0b111
        masterFileDir["小许"]?.get(8)?.protectCode = 0b111
    }


}