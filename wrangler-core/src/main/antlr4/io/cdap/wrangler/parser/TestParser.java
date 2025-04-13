/*
 * Copyright © 2017-2019 Cask Data, Inc.
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
 * See the License for the specific language governing permissions and limitations under the License.
 */

package io.cdap.wrangler.parser;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class TestParser {
    public static void main(String[] args) throws Exception {
        // Sample input text to test parsing
        String input = "set column :name to 'hello';";

        // Create an input stream from the string
        CharStream charStream = CharStreams.fromString(input);

        // Create a lexer instance
        DirectivesLexer lexer = new DirectivesLexer(charStream);

        // Tokenize the input
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // Create a parser instance
        DirectivesParser parser = new DirectivesParser(tokens);

        // Start parsing using the top-level rule "recipe"
        ParseTree tree = parser.recipe();

        // Print the parse tree for inspection
        System.out.println(tree.toStringTree(parser));
    }
}
