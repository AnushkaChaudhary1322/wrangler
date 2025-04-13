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
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DirectivesParserTest {

    private ParseTree parse(String input) {
        CharStream charStream = CharStreams.fromString(input);
        DirectivesLexer lexer = new DirectivesLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        DirectivesParser parser = new DirectivesParser(tokens);
        return parser.recipe(); // starting rule
    }

    @Test
    public void testBasicSetDirective() {
        String input = "set column :name to 'hello';";
        ParseTree tree = parse(input);
        assertNotNull(tree);
        assertTrue(tree.toStringTree().contains("set"));
    }

    @Test
    public void testParseByteSizeDirective() {
        String input = "parse-bytesize :size as :size_parsed;";
        ParseTree tree = parse(input);
        assertNotNull(tree);
        assertTrue(tree.toStringTree().contains("parse-bytesize"));
    }

    @Test
    public void testParseTimeDurationDirective() {
        String input = "parse-duration :duration as :duration_parsed;";
        ParseTree tree = parse(input);
        assertNotNull(tree);
        assertTrue(tree.toStringTree().contains("parse-duration"));
    }

    @Test
    public void testInvalidDirective() {
        String input = "bad-directive :oops;";
        CharStream charStream = CharStreams.fromString(input);
        DirectivesLexer lexer = new DirectivesLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        DirectivesParser parser = new DirectivesParser(tokens);

        parser.removeErrorListeners(); // Remove console error printing
        BaseErrorListener errorListener = new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line,
                    int charPositionInLine, String msg, RecognitionException e) {
                fail("Parser failed with error: " + msg);
            }
        };
        parser.addErrorListener(errorListener);

        parser.recipe(); // Should fail
    }
}
