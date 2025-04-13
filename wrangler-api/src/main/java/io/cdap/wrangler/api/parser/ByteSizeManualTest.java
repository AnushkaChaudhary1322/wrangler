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

import io.cdap.wrangler.api.parser.ByteSize;

/**
 * Manual test class for ByteSize.parse method.
 */

public class ByteSizeManualTest {
    public static void main(String[] args) {
        testParse("1KB", 1024L);
        testParse("1MiB", 1_048_576L);
        testParse("1.5KB", 1500L);
        testParse("1GiB", 1_073_741_824L);

        testInvalid("123XYZ");
        testInvalid("");
    }

    private static void testParse(String input, long expected) {
        try {
            long actual = ByteSize.parse(input);
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
            ByteSize.parse(input);
            System.out.println("FAIL: " + input + " should have thrown an exception.");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: " + input + " correctly threw IllegalArgumentException.");
        } catch (Exception e) {
            System.out.println("FAIL: " + input + " threw unexpected exception: " + e);
        }
    }
}
