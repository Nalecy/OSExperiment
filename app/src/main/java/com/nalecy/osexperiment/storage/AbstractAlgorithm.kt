package com.nalecy.osexperiment.storage

/**
 * @author nalecy
 * @since 2020/11/30
 */
abstract class AbstractAlgorithm(
        private val size: Long
) {

    private var headFreeSpace: Space = Space(true, 0,
            size, null, null)

    private val busySpaceList = mutableListOf<Space>()

    fun acquireSpace(acquireSize: Long): Space? {
        if (acquireSize > size) {
            TODO("处理越界")
        }
        val suitableSpace = findSuitableSpace(headFreeSpace, acquireSize)
                ?: return null
        val needBehindCut = acquireSize < suitableSpace.startAddress + suitableSpace.size

        if (needBehindCut) {
            cutSpaceBehind(suitableSpace, suitableSpace.size - acquireSize)
        }
        return removeSpace(suitableSpace)
    }

    /**
     * 在链中切掉 [originSpace] 后面长度为 [cutSize] 的 space 并返回
     * 长度不足返回 null
     */
    private fun cutSpaceBehind(originSpace: Space, cutSize: Long): Space? {
        if (originSpace.size < cutSize) return null
        return Space(true, originSpace.startAddress + cutSize, cutSize, null, null)
                .also {
                    originSpace.apply {
                        size -= cutSize
                        it.next = next
                        it.prev = this
                        next = it
                    }
                }
    }

    /**
     * 在链中切掉 [originSpace] 前面面长度为 [cutSize] 的 space 并返回
     * 长度不足返回 null
     */
    private fun cutSpaceFront(originSpace: Space, cutSize: Long): Space? {
        if (originSpace.size < cutSize) return null
        return Space(true, originSpace.startAddress, cutSize, null, null)
                .also {
                    originSpace.apply {
                        size -= cutSize
                        startAddress += cutSize
                        it.prev = prev
                        it.next = this
                        prev = it
                    }
                }
    }

    private fun removeSpace(space: Space) = space.apply {
        next?.prev = prev
        prev?.next = next
        next = null
        prev = null
    }

    fun returnBackSpace(space: Space) {
        if (space.isFrontThan(headFreeSpace)) {
            headFreeSpace.prev = space
            space.next = headFreeSpace
            headFreeSpace = space
        } else {
            var walk: Space? = headFreeSpace
            var last: Space? = null
            while (walk != null) {
                if (space.isFrontThan(walk)) {
                    space.next = walk
                    space.prev = walk.prev
                    walk.prev = space
                    space.prev!!.next = space
                    break
                }
                last = walk
                walk = walk.next
            }
            if (walk == null) {
                last?.next = space
                space.prev = last
            }
        }
        //插入完毕，开始处理合并
        space.prev?.apply {
            if (startAddress + size == space.startAddress) {
                space.startAddress = startAddress
                space.size += size
                space.prev = prev
                prev?.next = space
            }
        }
        space.next?.apply {
            if (space.startAddress + space.size == startAddress) {
                space.size += size
                space.next = next
                next?.prev = space
            }
        }
    }

    abstract fun findSuitableSpace(head: Space?, size: Long): Space?

    fun free(space: Space) = returnBackSpace(space.apply {
        free = true
        busySpaceList.remove(this)
    })


    fun alloc(size: Long): Space? = acquireSpace(size)?.apply {
        free = false
        busySpaceList.add(this)
    }

}