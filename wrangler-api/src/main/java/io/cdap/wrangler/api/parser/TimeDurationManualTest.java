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
 * Manual test class for TimeDuration.parse method.
 */

public class TimeDurationManualTest {
    public static void main(String[] args) {
        testParse("1s", 1000L);
        testParse("10m", 600_000L);
        testParse("2h", 7_200_000L);
        testParse("1d", 86_400_000L);
        testParse("3w", 18_144_000_000L);
        testParse("2mo", 5_259_492_000L); // Approx. for 2 months
        testParse("1y", 31_557_600_000L); // Approx. for 1 year

        testInvalid("10xy");
        testInvalid("");
        testInvalid("5.5s"); // Invalid as only integer values are supported
    }

    private static void testParse(String input, long expected) {
        try {
            long actual = TimeDuration.parse(input);
            if (actual == expected) {
                System.out.println("PASS: " + input + " -> " + actual);
            } else {
                System.out.println("FAIL: " + input + " -> " + actual + " (expected " + expected + ")");
            }
        } catch (Exception e) {
            System.out.println("FAIL: " + input + " threw an exception: " + e.getMessage());
        }
    }

    private static void testInvalid(String input) {
        try {
            TimeDuration.parse(input);
            System.out.println("FAIL: " + input + " should have thrown an exception.");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: " + input + " correctly threw IllegalArgumentException.");
        } catch (Exception e) {
            System.out.println("FAIL: " + input + " threw unexpected exception: " + e);
        }
    }
}
