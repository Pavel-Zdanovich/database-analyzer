package com.example.databaseanalyzer.web;

public class AnalyzerException extends RuntimeException {

    public AnalyzerException() {
    }

    public AnalyzerException(String message) {
        super(message);
    }

    public AnalyzerException(String message, Throwable cause) {
        super(message, cause);
    }

    public AnalyzerException(Throwable cause) {
        super(cause);
    }
}
