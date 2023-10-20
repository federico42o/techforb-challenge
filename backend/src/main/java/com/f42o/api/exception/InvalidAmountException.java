package com.f42o.api.exception;

public class InvalidAmountException extends RuntimeException{

    public InvalidAmountException(){
        super("Invalid amount.");
    }
}
