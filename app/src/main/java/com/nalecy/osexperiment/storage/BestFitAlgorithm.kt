package com.nalecy.osexperiment.storage

/**
 * @author nalecy
 * @since 2020/12/1
 */
class BestFitAlgorithm(initSize: Long) : AbstractAlgorithm(initSize) {

    override fun findSuitableSpace(head: Space?, size: Long): Space? {
        var walk = head
        var suitableSpace: Space? = null
        while (walk != null) {
            if (walk.size > size) {
                if (suitableSpace == null || walk.size - size < suitableSpace.size - size) {
                    suitableSpace = walk
                }
            }
            walk = walk.next
        }
        return suitableSpace
    }

}