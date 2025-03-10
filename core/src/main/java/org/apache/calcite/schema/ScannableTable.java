/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.calcite.schema;

import org.apache.calcite.DataContext;
import org.apache.calcite.linq4j.Enumerable;

import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Table that can be scanned without creating an intermediate relational
 * expression.
 * 可被扫描的表，无需创建中间关系表达式。
 */
public interface ScannableTable extends Table {
  /**
   * Returns an enumerator over the rows in this Table. Each row is represented
   * as an array of its column values.
   * 返回此表中行的枚举器。每一行被表示为包含其列值的数组。
   */
  Enumerable<@Nullable Object[]> scan(DataContext root);
}
