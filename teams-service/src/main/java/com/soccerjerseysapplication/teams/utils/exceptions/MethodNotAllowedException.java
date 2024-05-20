package com.soccerjerseysapplication.teams.utils.exceptions;

public class MethodNotAllowedException extends RuntimeException{

    public MethodNotAllowedException() {}
    public MethodNotAllowedException(String message) { super(message); }
    public MethodNotAllowedException(Throwable cause) { super(cause); }
    public MethodNotAllowedException(String message, Throwable cause) {
        super(message, cause); }
}
