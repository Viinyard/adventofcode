package dev.vinyard.adventofcode.soluce.year2024.day3;

import org.antlr.v4.runtime.DefaultErrorStrategy;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Token;

public class SkipMulErrorStrategy extends DefaultErrorStrategy {
    private boolean errorInMul = false;

    public boolean isErrorInMul() {
        return errorInMul;
    }

    @Override
    public void recover(Parser recognizer, RecognitionException e) {
        if (recognizer.getRuleContext().getRuleIndex() == recognizer.getRuleIndex("mul")) {
            errorInMul = true;
            // Skip the entire mul rule
            while (recognizer.getInputStream().LA(1) != Token.EOF && recognizer.getInputStream().LA(1) != ')') {
                recognizer.consume();
            }
            if (recognizer.getInputStream().LA(1) == ')') {
                recognizer.consume(); // consume the closing ')'
            }
        } else {
            super.recover(recognizer, e);
        }
    }

    @Override
    public Token recoverInline(Parser recognizer) throws RecognitionException {
        if (recognizer.getRuleContext().getRuleIndex() == recognizer.getRuleIndex("mul")) {
            errorInMul = true;
            // Skip the entire mul rule
            while (recognizer.getInputStream().LA(1) != Token.EOF && recognizer.getInputStream().LA(1) != ')') {
                recognizer.consume();
            }
            if (recognizer.getInputStream().LA(1) == ')') {
                recognizer.consume(); // consume the closing ')'
            }
            return recognizer.getCurrentToken();
        } else {
            return super.recoverInline(recognizer);
        }
    }
}