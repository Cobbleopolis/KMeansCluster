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
}
