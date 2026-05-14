

package org.example;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import org.example.cv.antlr.CvDslLexer;
import org.example.cv.antlr.CvDslParser;

public class Main {
    public static void main(String[] args) throws Exception {

        CharStream input = CharStreams.fromFileName("src/main/resources/test.txt");

        CvDslLexer lexer = new CvDslLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        CvDslParser parser = new CvDslParser(tokens);

        ParseTree tree = parser.cv_document();

        System.out.println(tree.toStringTree(parser));
    }
}