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
package org.apache.calcite.sql;

import org.apache.calcite.sql.parser.SqlParserPos;
import org.apache.calcite.sql.util.SqlVisitor;
import org.apache.calcite.sql.validate.SqlMonotonicity;
import org.apache.calcite.sql.validate.SqlValidator;
import org.apache.calcite.sql.validate.SqlValidatorScope;
import org.apache.calcite.util.Litmus;

import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * A <code>SqlDynamicParam</code> represents a dynamic parameter marker in an
 * SQL statement. The textual order in which dynamic parameters appear within an
 * SQL statement is the only property which distinguishes them, so this 0-based
 * index is recorded as soon as the parameter is encountered.
 *
 * <code>SqlDynamicParam</code> 表示 SQL 语句中的动态参数标记。动态参数在 SQL 语句中出现的文本顺序是唯一区分它们的属性，因此一旦遇到参数，就会记录这个从0开始的索引。
 */
public class SqlDynamicParam extends SqlNode {
  //~ Instance fields --------------------------------------------------------

  private final int index;

  //~ Constructors -----------------------------------------------------------

  public SqlDynamicParam(
      int index,
      SqlParserPos pos) {
    super(pos);
    this.index = index;
  }

  //~ Methods ----------------------------------------------------------------

  @Override public SqlNode clone(SqlParserPos pos) {
    return new SqlDynamicParam(index, pos);
  }

  @Override public SqlKind getKind() {
    return SqlKind.DYNAMIC_PARAM;
  }

  public int getIndex() {
    return index;
  }

  @Override public void unparse(
      SqlWriter writer,
      int leftPrec,
      int rightPrec) {
    writer.dynamicParam(index);
  }

  @Override public void validate(SqlValidator validator, SqlValidatorScope scope) {
    validator.validateDynamicParam(this);
  }

  @Override public SqlMonotonicity getMonotonicity(SqlValidatorScope scope) {
    return SqlMonotonicity.CONSTANT;
  }

  @Override public <R> R accept(SqlVisitor<R> visitor) {
    return visitor.visit(this);
  }

  @Override public boolean equalsDeep(@Nullable SqlNode node, Litmus litmus) {
    if (!(node instanceof SqlDynamicParam)) {
      return litmus.fail("{} != {}", this, node);
    }
    SqlDynamicParam that = (SqlDynamicParam) node;
    if (this.index != that.index) {
      return litmus.fail("{} != {}", this, node);
    }
    return litmus.succeed();
  }
}
