package org.example;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import org.example.*;
import org.example.cv.antlr.*;

import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

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

        String html = cv.toHtml();
        Files.writeString(Path.of("output.html"), html);
        System.out.println("Zapisano output.html");

        boolean exportPdf = cv.getConfig() != null
                && cv.getConfig().getBooleanField("EXPORT_PDF");

        if (exportPdf) {
            exportToPdf(html);
            System.out.println("Zapisano output.pdf");
        } else {
            System.out.println("EXPORT_PDF=FALSE — pomijam PDF");
        }
    }
    private static void exportToPdf(String html) throws Exception {
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();

        try (FileOutputStream fos = new FileOutputStream("output.pdf")) {
            renderer.createPDF(fos);
        }
    }
}