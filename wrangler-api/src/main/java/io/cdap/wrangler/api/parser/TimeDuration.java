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

package io.cdap.wrangler.api.parser;

/**
 * Utility class to parse duration strings (e.g., "5s", "10m", "1h") into
 * milliseconds.
 */
public class TimeDuration {

    /**
     * Parses a duration string like "10s", "5m", "1h", "3d", etc. into
     * milliseconds.
     *
     * @param input The input duration string (e.g., "10s", "2h", "5m")
     * @return the duration in milliseconds
     * @throws IllegalArgumentException if the format or unit is invalid
     */
    public static long parse(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Duration input is null or empty.");
        }

        String trimmed = input.trim().toLowerCase();

        // Extract numeric part and unit part
        int i = 0;
        while (i < trimmed.length() && Character.isDigit(trimmed.charAt(i))) {
            i++;
        }

        if (i == 0 || i == trimmed.length()) {
            throw new IllegalArgumentException("Invalid duration format: " + input);
        }

        String numberStr = trimmed.substring(0, i);
        String unit = trimmed.substring(i);

        long number;
        try {
            number = Long.parseLong(numberStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number in duration: " + numberStr);
        }

        switch (unit) {
            case "ms":
                return number;
            case "s":
                return number * 1_000; // seconds to milliseconds
            case "m":
                return number * 60_000; // minutes to milliseconds
            case "h":
                return number * 3_600_000; // hours to milliseconds
            case "d":
                return number * 86_400_000; // days to milliseconds
            case "w":
                return number * 604_800_000; // weeks to milliseconds
            case "mo":
                return number * 2_629_746_000L; // months to milliseconds (approx. 30.44 days)
            case "y":
                return number * 31_557_600_000L; // years to milliseconds (approx. 365.25 days)
            default:
                throw new IllegalArgumentException("Unknown duration unit: " + unit);
        }
    }
}
