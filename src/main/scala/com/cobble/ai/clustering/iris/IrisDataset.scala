package com.cobble.ai.clustering.iris

import com.cobble.ai.clustering.core.Dataset

import scala.io.Source

class IrisDataset(resourceLocation: String = "iris.csv") extends Dataset[Iris](Source.fromResource(resourceLocation)) {

    override def mapData(map: Map[String, String]): Iris = {
        Iris(
            map.getOrElse("id", "0").toInt,
            map.getOrElse("sepalLengthCm", "0").toFloat,
            map.getOrElse("sepalWidthCm", "0").toFloat,
            map.getOrElse("petalLengthCm", "0").toFloat,
            map.getOrElse("petalWidthCm", "0").toFloat,
            Symbol(map.getOrElse("species", "Unknown"))
        )
    }

    override def getMinMaxMap(data: Array[Iris] = dataSet): Map[Symbol, (Float, Float)] = {
        val sepalLengthCmArr: Array[Float] = data.map(_.sepalLengthCm)
        val sepalWidthCmArr: Array[Float] = data.map(_.sepalWidthCm)
        val petalLengthCmArr: Array[Float] = data.map(_.petalLengthCm)
        val petalWidthCmArr: Array[Float] = data.map(_.petalWidthCm)
        Map(
            'sepalLengthCm -> (sepalLengthCmArr.min, sepalLengthCmArr.max),
            'sepalWidthCm -> (sepalWidthCmArr.min, sepalWidthCmArr.max),
            'petalLengthCm -> (petalLengthCmArr.min, petalLengthCmArr.max),
            'petalWidthCm -> (petalWidthCmArr.min, petalWidthCmArr.max)
        )
    }
}
