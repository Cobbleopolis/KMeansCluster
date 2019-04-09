package com.cobble.ai.clustering.core

import com.github.tototoshi.csv.CSVReader

import scala.io.Source
import scala.reflect.ClassTag
import scala.util.Random

abstract class Dataset[T](source: Source, rngSeed: Long = System.currentTimeMillis())(implicit classTag: ClassTag[T]) {

    val random: Random = new Random(rngSeed)

    val csvReader: CSVReader = CSVReader.open(source)

    val dataSet: Array[T] = csvReader.allWithHeaders().map(mapData).toArray

    val dataSize: Int = dataSet.length

    /**
      * This is the function that maps the data from the CSV file to the class of the Dataset.
      *
      * @param map A map containing a single row from the csv, the key is the header of the column.
      * @return A class of type `T`
      */
    protected def mapData(map: Map[String, String]): T

    def getMinMaxMap(data: Array[T] = dataSet): Map[Symbol, (Float, Float)]

    /**
      * Randomly samples the dataset. No entry will be repeated.
      *
      * @param n The number of samples taken from the dataset.
      * @return An array containing a random subset of the dataset.
      */
    def sampleData(n: Int): Array[T] = random.shuffle(dataSet.toList).take(n).toArray

}
