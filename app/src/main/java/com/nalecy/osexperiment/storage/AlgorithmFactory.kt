package com.nalecy.osexperiment.storage

/**
 * @author nalecy
 * @since 2020/11/30
 */
object AlgorithmFactory {
    private const val INIT_SIZE = 600L

    fun getFirstFitAlgorithm(): AbstractAlgorithm  =  FirstFitAlgorithm(INIT_SIZE)

    fun getBestFitAlgorithm(): AbstractAlgorithm  =  BestFitAlgorithm(INIT_SIZE)

}