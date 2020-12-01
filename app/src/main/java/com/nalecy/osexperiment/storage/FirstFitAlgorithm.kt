package com.nalecy.osexperiment.storage

/**
 * @author nalecy
 * @since 2020/11/30
 */
class FirstFitAlgorithm(initSize: Long) :AbstractAlgorithm(initSize) {
    override fun findSuitableSpace(head: Space?, size: Long): Space? {
        var walk = head
        while (walk != null){
            if (walk.size >= size ){
                break
            }
            walk = walk.next
        }
        return walk
    }

}