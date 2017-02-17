package com.databricks.spark.sql.perf.runner

import org.apache.spark.sql.SparkSession

import com.databricks.spark.sql.perf.tpcds.Tables


object DataGenerator {

  def main(args: Array[String]): Unit = {
    val dsdgenPath = args(0)
    val scaleFactor = args(1).toInt
    val tableLocation = args(2)

    val sparkSession = SparkSession.builder().appName("Spark SQL basic example")
      .getOrCreate()

    if (args.length > 4) {
      sparkSession.sparkContext.hadoopConfiguration.set("fs.s3.impl",
        "org.apache.hadoop.fs.s3native.NativeS3FileSystem")
      sparkSession.sparkContext.hadoopConfiguration.set("fs.s3.awsAccessKeyId",
        args(3))
      sparkSession.sparkContext.hadoopConfiguration.set("fs.s3.awsSecretAccessKey",
        args(4))
    }
    val genData = args(5).toBoolean
    import com.databricks.spark.sql.perf.tpcds.Tables
    val tables = new Tables(sparkSession.sqlContext, dsdgenPath, scaleFactor)

    if (genData) {
      tables.genData(tableLocation, "parquet",
        overwrite = true, partitionTables = true, useDoubleForDecimal = false,
        clusterByPartitionColumns = true, filterOutNullPartitionValues = true)
    }

    // Create metastore tables in a specified database for your data.
    // Once tables are created, the current database will be switched to the specified database.
    tables.createExternalTables(tableLocation, "parquet", "db1", overwrite = true)

    // Setup TPC-DS experiment
    import com.databricks.spark.sql.perf.tpcds.TPCDS
    val tpcds = new TPCDS (sqlContext = sparkSession.sqlContext)

  }
}
