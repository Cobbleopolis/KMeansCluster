package com.cobble.ai.clustering

import com.cobble.ai.clustering.iris.{Iris, IrisDataset}

import scala.util.Random

object Main {

    val TRAINING_SIZE: Float = 0.7f

    val random: Random = new Random(System.currentTimeMillis())

    def main(args: Array[String]): Unit = {
        val dataset: IrisDataset = new IrisDataset(rngSeed = 5)
        val centroids: Map[Symbol, Iris] = train(dataset)
        println(centroids)

    }

    def train(dataset: IrisDataset, numberOfCentroids: Int = 3, trainingSize: Float = TRAINING_SIZE): Map[Symbol, Iris] = {
        val set: Array[Iris] = dataset.sampleData(Math.ceil(dataset.dataSize * trainingSize).toInt)
        val minMaxMap = dataset.getMinMaxMap(set)
        val normalData: Array[Iris] = set.map(_.getNormalizedValues(minMaxMap))
        val clusterMap: Map[Symbol, Iris] = (0 until numberOfCentroids).map(i => (Symbol(s"cluster$i"), Iris(
            -1,
            Random.nextFloat(),
            Random.nextFloat(),
            Random.nextFloat(),
            Random.nextFloat(),
            Symbol(s"cluster$i")
        ))).toMap
        val closestCentroidArr: Array[(Symbol, Iris)] = createClosestCentroidArray(normalData, clusterMap)
        val closestCentroidMap: Map[Symbol, Array[Iris]] = createClosestCentroidMap(closestCentroidArr)
        closestCentroidMap.foreach(x => println(s"(${x._1}, ${x._2.mkString(", ")})"))
        clusterMap
    }

    def createClosestCentroidArray(normalData: Array[Iris], clusterMap: Map[Symbol, Iris]): Array[(Symbol, Iris)] =
        normalData.map { iris =>
            (clusterMap.mapValues(iris.getDistance).minBy(_._2)._1, iris)
        }

    def createClosestCentroidMap(closestCentroidArr: Array[(Symbol, Iris)]): Map[Symbol, Array[Iris]] =
        closestCentroidArr.groupBy(_._1).mapValues(x => x.map(_._2))

//    def createCentroids(closestCentroidMap: Map[Symbol, Array[Iris]])
}
