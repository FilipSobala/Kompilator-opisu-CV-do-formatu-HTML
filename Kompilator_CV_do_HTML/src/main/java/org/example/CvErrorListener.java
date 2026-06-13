package org.example;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

public class CvErrorListener extends BaseErrorListener {

    private final String filePath;

    public CvErrorListener(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                             int line, int charPositionInLine, String msg,
                             RecognitionException e) {
        System.err.printf("%s:%d:%d: error: %s%n",
                filePath, line, charPositionInLine + 1, msg);
    }
}