/*
 * Copyright © 2024 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.cdap.wrangler.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class to parse byte size strings (e.g., "10KB", "5.5MiB") to bytes.
 */
public class ByteSizeParser {
    private static final Map<String, Long> DECIMAL_UNITS = new HashMap<>();
    private static final Map<String, Long> BINARY_UNITS = new HashMap<>();

    static {
        // Decimal units (powers of 10)
        DECIMAL_UNITS.put("B", 1L);
        DECIMAL_UNITS.put("KB", 1_000L);
        DECIMAL_UNITS.put("MB", 1_000_000L);
        DECIMAL_UNITS.put("GB", 1_000_000_000L);
        DECIMAL_UNITS.put("TB", 1_000_000_000_000L);
        DECIMAL_UNITS.put("PB", 1_000_000_000_000_000L);

        // Binary units (powers of 2)
        BINARY_UNITS.put("B", 1L);
        BINARY_UNITS.put("KIB", 1024L);
        BINARY_UNITS.put("MIB", 1024L * 1024);
        BINARY_UNITS.put("GIB", 1024L * 1024 * 1024);
        BINARY_UNITS.put("TIB", 1024L * 1024 * 1024 * 1024);
        BINARY_UNITS.put("PIB", 1024L * 1024 * 1024 * 1024 * 1024);
    }

    /**
     * Parses a byte size string like "10MB", "5.5MiB", etc. into bytes.
     *
     * @param input The input string
     * @return the size in bytes
     * @throws IllegalArgumentException if the format or unit is invalid
     */
    public static long parse(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Byte size input is null or empty.");
        }

        String trimmed = input.trim().toUpperCase();

        // Extract numeric part and unit part
        int i = 0;
        while (i < trimmed.length() &&
                (Character.isDigit(trimmed.charAt(i)) || trimmed.charAt(i) == '.')) {
            i++;
        }

        if (i == 0 || i == trimmed.length()) {
            throw new IllegalArgumentException("Invalid byte size format: " + input);
        }

        String numberStr = trimmed.substring(0, i);
        String unit = trimmed.substring(i).toUpperCase();

        double number;
        try {
            number = Double.parseDouble(numberStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number in byte size: " + numberStr);
        }

        Long multiplier = DECIMAL_UNITS.get(unit);
        if (multiplier == null) {
            multiplier = BINARY_UNITS.get(unit);
        }

        if (multiplier == null) {
            throw new IllegalArgumentException("Unknown byte size unit: " + unit);
        }

        return (long) (number * multiplier);
    }
}
