package org.example;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import org.example.*;
import org.example.cv.antlr.*;;

public class Main {

    public static void main(String[] args) throws Exception {

        CharStream input = CharStreams.fromFileName(
                "src/main/resources/test.txt"
        );

        CvDslLexer lexer = new CvDslLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        CvDslParser parser = new CvDslParser(tokens);
        ParseTree tree = parser.cv_document();

        CvBuilder builder = new CvBuilder();
        Cv cv =(Cv) builder.visit(tree);

        System.out.println("CV");
        System.out.println(cv);

        System.out.println("\nPARSE TREE");
        System.out.println(tree.toStringTree(parser));
    }
}