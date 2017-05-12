/*
 * Copyright (C) 2017 Microsoft
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.microsoft.spark.perf.sql.report

import com.microsoft.spark.perf.report.{BenchmarkResult, Failure}

/**
 * The result of a query.
 *
 * @param name The name of the query.
 * @param mode The ExecutionMode of this run.
 * @param parameters Additional parameters that describe this query.
 * @param joinTypes The type of join operations in the query.
 * @param tables The tables involved in the query.
 * @param parsingTime The time used to parse the query.
 * @param analysisTime The time used to analyze the query.
 * @param optimizationTime The time used to optimize the query.
 * @param planningTime The time used to plan the query.
 * @param executionTime The time used to execute the query.
 * @param result the result of this run. It is not necessarily the result of the query.
 *               For example, it can be the number of rows generated by this query or
 *               the sum of hash values of rows generated by this query.
 * @param breakDown The breakdown results of the query plan tree.
 */
class SQLBenchmarkResult(
    name: String,
    mode: String,
    parameters: Map[String, String] = Map.empty[String, String],
    joinTypes: Seq[String] = Nil,
    tables: Seq[String] = Nil,
    parsingTime: Option[Double] = None,
    analysisTime: Option[Double] = None,
    optimizationTime: Option[Double] = None,
    planningTime: Option[Double] = None,
    override val executionTime: Option[Double] = None,
    result: Option[Long] = None,
    breakDown: Seq[SQLBreakdownResult] = Nil,
    queryExecution: Option[String] = None,
    override val failure: Option[Failure] = None)
  extends BenchmarkResult(name, mode, parameters, executionTime, failure)


/**
 * The execution time of a subtree of the query plan tree of a specific query.
 *
 * @param nodeName The name of the top physical operator of the subtree.
 * @param nodeNameWithArgs The name and arguments of the top physical operator of the subtree.
 * @param index The index of the top physical operator of the subtree
 *              in the original query plan tree. The index starts from 0
 *              (0 represents the top physical operator of the original query plan tree).
 * @param executionTime The execution time of the subtree.
 */
case class SQLBreakdownResult(
    nodeName: String,
    nodeNameWithArgs: String,
    index: Int,
    children: Seq[Int],
    executionTime: Double,
    delta: Double)
