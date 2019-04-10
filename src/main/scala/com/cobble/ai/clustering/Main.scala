package com.cobble.ai.clustering

import com.cobble.ai.clustering.iris.{Iris, IrisDataset}

import scala.util.Random

object Main {

    val TRAINING_SIZE: Float = 0.7f

    val CONVERGENCE_THRESHOLD: Float = 0.95f

    val random: Random = new Random(System.currentTimeMillis())

    def main(args: Array[String]): Unit = {
        val dataset: IrisDataset = new IrisDataset(rngSeed = 5)
        val centroids: Map[Symbol, Iris] = train(dataset)
        println(centroids)

    }

    def train(dataset: IrisDataset,
              numberOfCentroids: Int = 3,
              trainingSize: Float = TRAINING_SIZE,
              convergenceThreshold: Float = CONVERGENCE_THRESHOLD): Map[Symbol, Iris] = {
        val set: Array[Iris] = dataset.sampleData(Math.ceil(dataset.dataSize * trainingSize).toInt)
        val minMaxMap = dataset.getMinMaxMap(set)
        val normalData: Array[Iris] = set.map(_.getNormalizedValues(minMaxMap))
        var clusterMap: Map[Symbol, Iris] = (0 until numberOfCentroids).map(i => (Symbol(s"cluster$i"), Iris(
            -1,
            Random.nextFloat(),
            Random.nextFloat(),
            Random.nextFloat(),
            Random.nextFloat(),
            Symbol(s"cluster$i")
        ))).toMap
        val closestCentroidArr: Array[(Symbol, Iris)] = createClosestCentroidArray(normalData, clusterMap)
        var lastClosestCentroidMap: Map[Symbol, Array[Iris]] = createClosestCentroidMap(closestCentroidArr)
        var currentConvergenceMap: Map[Symbol, Float] = clusterMap.mapValues(_ => 0f)
        while (currentConvergenceMap.exists(_._2 < convergenceThreshold)) {
            clusterMap = createCentroids(lastClosestCentroidMap)
            val closestConvergenceMap: Map[Symbol, Array[Iris]] =
                createClosestCentroidMap(createClosestCentroidArray(normalData, clusterMap))
            currentConvergenceMap = closestConvergenceMap.map { case (sym, clusterData) =>
                (sym, clusterData.count(lastClosestCentroidMap(sym).contains) / clusterData.length.toFloat)
            }
            lastClosestCentroidMap = closestConvergenceMap
            println(clusterMap)
        }
        clusterMap.map { case (sym, centroid) =>
            val speciesList: Array[Symbol] = lastClosestCentroidMap(sym).map(_.species)
            val speciesSymbol: Symbol = speciesList.distinct.map(sym => (sym, speciesList.count(s => s == sym))).toMap.maxBy(_._2)._1
            (speciesSymbol, centroid)
        }
    }

    def createClosestCentroidArray(normalData: Array[Iris], clusterMap: Map[Symbol, Iris]): Array[(Symbol, Iris)] =
        normalData.map { iris =>
            (clusterMap.mapValues(iris.getDistance).minBy(_._2)._1, iris)
        }

    def createClosestCentroidMap(closestCentroidArr: Array[(Symbol, Iris)]): Map[Symbol, Array[Iris]] =
        closestCentroidArr.groupBy(_._1).mapValues(x => x.map(_._2))

    def createCentroids(closestCentroidMap: Map[Symbol, Array[Iris]]): Map[Symbol, Iris] = {
        closestCentroidMap.map { case (centroidSymbol, irisList) =>
            val sepalLengthCmArr: Array[Float] = irisList.map(_.sepalLengthCm)
            val sepalWidthCmArr: Array[Float] = irisList.map(_.sepalWidthCm)
            val petalLengthCmArr: Array[Float] = irisList.map(_.petalLengthCm)
            val petalWidthCmArr: Array[Float] = irisList.map(_.petalWidthCm)
            (centroidSymbol, Iris(
                -1,
                sepalLengthCmArr.sum / sepalLengthCmArr.length,
                sepalWidthCmArr.sum / sepalWidthCmArr.length,
                petalLengthCmArr.sum / petalLengthCmArr.length,
                petalWidthCmArr.sum / petalWidthCmArr.length,
                centroidSymbol
            ))
        }
    }
}
