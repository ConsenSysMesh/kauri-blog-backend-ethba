package io.kauri.dbt.model.exception;

public class DBTException extends Exception {

    private static final long serialVersionUID = -4813450229419346372L;

    public DBTException(String message) {
        super(message);
    }

    public DBTException(String message, Throwable e) {
        super(message, e);
    }
    
}
