package org.superbiz.tf;

public class QMLNodeProcessingException extends RuntimeException {
    public QMLNodeProcessingException(Throwable e) {
        super(e);
    }

    public QMLNodeProcessingException(String message) {
        super(message);
    }
}
