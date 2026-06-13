package org.example;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.example.cv.antlr.CvDslLexer;
import org.example.cv.antlr.CvDslParser;
import org.xhtmlrenderer.pdf.ITextRenderer;

public class Main {

    public static void main(String[] args) throws Exception {

        String filePath = args.length > 0 ? args[0] : "src/main/resources/test.cv";
        CharStream input = CharStreams.fromFileName(filePath);

        CvDslLexer lexer = new CvDslLexer(input);
        lexer.removeErrorListeners();
        lexer.addErrorListener(new CvErrorListener(filePath));

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        CvDslParser parser = new CvDslParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(new CvErrorListener(filePath));

        ParseTree tree = parser.cv_document();

        CvBuilder builder = new CvBuilder(filePath);
        Cv cv =(Cv) builder.visit(tree);

        System.out.println("CV");
        System.out.println(cv);

        String html = cv.toHtml();

        String baseName = Path.of(filePath).getFileName().toString().replaceFirst("\\.cv$", "");
        String outputName = "output_" + baseName + ".html";

        Path outputPath = Path.of(outputName).toAbsolutePath();
        Files.writeString(outputPath, html);
        System.out.println("Zapisano " + outputName);

        java.awt.Desktop.getDesktop().browse(outputPath.toUri());

        boolean exportPdf = cv.getConfig() != null
                && cv.getConfig().getBooleanField("EXPORT_PDF");

        if (exportPdf) {
            String pdfName = "output_" + baseName + ".pdf";
            exportToPdf(html, pdfName);
            System.out.println("Zapisano " + pdfName);
        } else {
            System.out.println("EXPORT_PDF=FALSE — pomijam PDF");
        }
    }
    private static void exportToPdf(String html, String pdfName) throws Exception {
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();

        try (FileOutputStream fos = new FileOutputStream(pdfName)) {
            renderer.createPDF(fos);
        }
    }
}