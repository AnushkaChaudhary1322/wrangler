/*
 * Copyright © 2023 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.cdap.wrangler.utils;

import io.cdap.wrangler.api.Row;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Utility methods for {@link Row}
 */
public class RowHelper {

  private RowHelper() {
    throw new AssertionError("Cannot instantiate a static utility class");
  }

  /**
   * Creates a merged record after iterating through all rows.
   * Merges rows based on unique column names, preserving values.
   *
   * @param rows list of all rows.
   * @return A single merged row with combined columns.
   */
  public static Row createMergedRow(List<Row> rows) {
    Row merged = new Row();
    for (Row row : rows) {
      for (int i = 0; i < row.width(); ++i) {
        Object o = row.getValue(i);
        if (o != null) {
          int idx = merged.find(row.getColumn(i));
          if (idx == -1) {
            merged.add(row.getColumn(i), o);
          }
        }
      }
    }
    return merged;
  }

  /**
   * Updates an existing row by replacing a column's value.
   * If the column exists, it will update the value, otherwise, it will add the
   * column with the new value.
   *
   * @param row    the row to update.
   * @param column the column name.
   * @param value  the new value for the column.
   */
  public static void updateRow(Row row, String column, Object value) {
    int idx = row.find(column);
    if (idx != -1) {
      row.setValue(idx, value); // Assuming there's a method to update a value by index
    } else {
      row.add(column, value);
    }
  }

  /**
   * Filters rows based on a specified condition for a given column.
   * The condition should return true for rows to be included in the result.
   *
   * @param rows      the list of rows to filter.
   * @param column    the column to apply the filter condition.
   * @param condition the condition to check (e.g., a lambda or custom predicate).
   * @return a list of filtered rows.
   */
  public static List<Row> filterRows(List<Row> rows, String column, RowCondition condition) {
    return rows.stream()
        .filter(row -> condition.test(row.getValue(row.find(column))))
        .collect(Collectors.toList()); // Use Collectors.toList() for Java 11
  }

  /**
   * Aggregates rows by a specified column, summing the values of another numeric
   * column.
   *
   * @param rows            the list of rows to aggregate.
   * @param groupByColumn   the column to group by.
   * @param aggregateColumn the column to sum.
   * @return a map of aggregated values, where keys are unique values from the
   *         groupByColumn
   *         and values are the sum of the aggregateColumn for each group.
   */
  public static Map<Object, Double> aggregateRows(List<Row> rows, String groupByColumn, String aggregateColumn) {
    Map<Object, Double> aggregated = new HashMap<>();
    for (Row row : rows) {
      Object groupByValue = row.getValue(row.find(groupByColumn));
      Double aggregateValue = (Double) row.getValue(row.find(aggregateColumn));

      aggregated.put(groupByValue, aggregated.getOrDefault(groupByValue, 0.0) + aggregateValue);
    }
    return aggregated;
  }

  /**
   * A helper interface for row conditions used in filtering.
   */
  @FunctionalInterface
  public interface RowCondition {
    boolean test(Object value);
  }

}
