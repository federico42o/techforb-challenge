package com.f42o.api.exception;

public class BankAccountNotFoundException extends RuntimeException{

    public BankAccountNotFoundException() {
        super("The account doesn't exist.");
    }
}
