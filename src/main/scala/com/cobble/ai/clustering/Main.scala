package com.cobble.ai.clustering

import com.cobble.ai.clustering.iris.IrisDataset

object Main {

    def main(args: Array[String]): Unit = {
        val dataset: IrisDataset = new IrisDataset
        dataset.dataSet.foreach(println)
    }

}
