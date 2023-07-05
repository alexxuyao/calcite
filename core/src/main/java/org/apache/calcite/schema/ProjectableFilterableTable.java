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
import org.apache.calcite.rex.RexNode;

import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;

/**
 * Table that can be scanned, optionally applying supplied filter expressions,
 * and projecting a given list of columns,
 * without creating an intermediate relational expression.
 *
 * <p>If you wish to write a table that can apply projects but not filters,
 * simply decline all filters.
 *
 * 可以扫描的表，可以选择性地应用提供的过滤表达式，并投影给定的列列表，而无需创建中间关系表达式。
 * 如果您希望编写一个可以应用投影但不应用过滤器的表，只需拒绝所有过滤器即可。
 *
 * @see ScannableTable
 * @see FilterableTable
 */
public interface ProjectableFilterableTable extends Table {
  /** Returns an enumerable over the rows in this Table.
   *
   * <p>Each row is represented as an array of its column values.
   *
   * <p>The list of filters is mutable.
   * If the table can implement a particular filter, it should remove that
   * filter from the list.
   * If it cannot implement a filter, it should leave it in the list.
   * Any filters remaining will be implemented by the consuming Calcite
   * operator.
   *
   * <p>The projects are zero-based.
   *
   * 返回此表中的行的可枚举集合。
   * 每一行被表示为一个包含其列值的数组。
   * 过滤器列表是可变的。如果表可以实现特定的过滤器，则应从列表中删除该过滤器。如果无法实现过滤器，则应将其保留在列表中。
   * 剩余的任何过滤器将由使用 Calcite 运算符进行实现。
   * 投影是从零开始计数的。
   *
   * @param root Execution context
   * @param filters Mutable list of filters. The method should keep in the
   *                list any filters that it cannot apply.
   * @param projects List of projects. Each is the 0-based ordinal of the column
   *                 to project. Null means "project all columns".
   * @return Enumerable over all rows that match the accepted filters, returning
   * for each row an array of column values, one value for each ordinal in
   * {@code projects}.
   */
  Enumerable<@Nullable Object[]> scan(DataContext root, List<RexNode> filters,
      int @Nullable [] projects);
}
