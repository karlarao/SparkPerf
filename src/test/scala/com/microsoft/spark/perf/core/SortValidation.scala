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

package com.microsoft.spark.perf.core

import scala.io.Source

private[core] object SortValidation {

  def validate(path: String, expectedNum: Int): Boolean = {
    var minimumKey = Long.MinValue
    var cnt = 0
    for (line <- Source.fromFile(path).getLines()) {
      if (line.toLong < minimumKey) {
        return false
      }
      cnt += 1
      minimumKey = line.toLong
    }
    assert(cnt == expectedNum)
    true
  }
}
