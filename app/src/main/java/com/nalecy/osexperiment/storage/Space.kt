package com.nalecy.osexperiment.storage

/**
 * @author nalecy
 * @since 2020/12/1
 */
class Space(
    var free: Boolean,
    var startAddress: Long,
    var size: Long,
    var prev: Space?,
    var next: Space?
) {
    fun isFrontThan(space: Space) = startAddress + size < space.startAddress

    fun isBehindThan(space: Space) = space.startAddress + space.size < startAddress
}