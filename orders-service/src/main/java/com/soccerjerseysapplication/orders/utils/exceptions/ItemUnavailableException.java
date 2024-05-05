package com.soccerjerseysapplication.orders.utils.exceptions;

public class ItemUnavailableException extends RuntimeException{

    public ItemUnavailableException() {}

    public ItemUnavailableException(String message) { super(message); }

    public ItemUnavailableException(Throwable cause) { super(cause); }

    public ItemUnavailableException(String message, Throwable cause) { super(message, cause); }
}
