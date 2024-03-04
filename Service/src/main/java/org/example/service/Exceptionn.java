package org.example.service;

public class Exceptionn extends Exception{
    public Exceptionn() {
    }
    public Exceptionn(String message) {
        super(message);
    }

    public Exceptionn(String message, Throwable cause) {
        super(message, cause);
    }
}
