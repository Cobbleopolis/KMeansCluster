package com.cobble.ai.clustering

import com.cobble.ai.clustering.iris.{Iris, IrisDataset}

import scala.util.Random

object Main {

    val TRAINING_SIZE: Float = 0.7f

    val random: Random = new Random(System.currentTimeMillis())

    def main(args: Array[String]): Unit = {
        val dataset: IrisDataset = new IrisDataset
        val centroids: Map[Symbol, Iris] = train(dataset)
        println(centroids)

    }

    def train(dataset: IrisDataset, numberOfCentroids: Int = 3, trainingSize: Float = TRAINING_SIZE): Map[Symbol, Iris] = {
        val set: Array[Iris] = dataset.sampleData(Math.ceil(dataset.dataSize * trainingSize).toInt)
        val minMaxMap = dataset.getMinMaxMap(set)
        val normalData: Array[Iris] = set.map(_.getNormalizedValues(minMaxMap))
        val sepalLengthCmArr: Array[Float] = normalData.map(_.sepalLengthCm)
        val sepalWidthCmArr: Array[Float] = normalData.map(_.sepalWidthCm)
        val petalLengthCmArr: Array[Float] = normalData.map(_.petalLengthCm)
        val petalWidthCmArr: Array[Float] = normalData.map(_.petalWidthCm)
//        val centroids: Array[Iris] = (0 to numberOfCentroids).map(Iris(
//            -1,
//            Random.nextFloat(),
//            Random.nextFloat(),
//            Random.nextFloat(),
//            Random.nextFloat(),
//            'Centroid
//        ))
        Map()
    }

}
