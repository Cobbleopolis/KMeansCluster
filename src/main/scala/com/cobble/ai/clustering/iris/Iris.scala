package com.cobble.ai.clustering.iris

case class Iris(id: Int, sepalLengthCm: Float, sepalWidthCm: Float, petalLengthCm: Float, petalWidthCm: Float, species: Symbol) {

    val isCluster: Boolean = id == -1

    def getNormalizedValues(minMaxMap: Map[Symbol, (Float, Float)]): Iris = Iris(
        id,
        (sepalLengthCm - minMaxMap('sepalLengthCm)._1) / (minMaxMap('sepalLengthCm)._2 - minMaxMap('sepalLengthCm)._1),
        (sepalWidthCm - minMaxMap('sepalWidthCm)._1) / (minMaxMap('sepalWidthCm)._2 - minMaxMap('sepalWidthCm)._1),
        (petalLengthCm - minMaxMap('petalLengthCm)._1) / (minMaxMap('petalLengthCm)._2 - minMaxMap('petalLengthCm)._1),
        (petalWidthCm - minMaxMap('petalWidthCm)._1) / (minMaxMap('petalWidthCm)._2 - minMaxMap('petalWidthCm)._1),
        species
    )

    def getNonNormalizedValues(minMaxMap: Map[Symbol, (Float, Float)]): Iris = Iris(
        id,
        ((minMaxMap('sepalLengthCm)._2 - minMaxMap('sepalLengthCm)._1) * sepalLengthCm) + minMaxMap('sepalLengthCm)._1,
        ((minMaxMap('sepalWidthCm)._2 - minMaxMap('sepalWidthCm)._1) * sepalWidthCm) + minMaxMap('sepalWidthCm)._1,
        ((minMaxMap('petalLengthCm)._2 - minMaxMap('petalLengthCm)._1) * petalLengthCm) + minMaxMap('petalLengthCm)._1,
        ((minMaxMap('petalWidthCm)._2 - minMaxMap('petalWidthCm)._1) * petalWidthCm) + minMaxMap('petalWidthCm)._1,
        species
    )

    def getDistance(other: Iris): Float = Math.sqrt(
        Math.pow(sepalLengthCm - other.sepalLengthCm, 2) +
        Math.pow(sepalWidthCm - other.sepalWidthCm, 2) +
        Math.pow(petalLengthCm - other.petalLengthCm, 2) +
        Math.pow(petalWidthCm - other.petalWidthCm, 2)
    ).toFloat

}
