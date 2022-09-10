package com.codefoe.exception;

public class BalanceNotSufficientException extends RuntimeException {
    public BalanceNotSufficientException(String insufficient_balance) {
        super(insufficient_balance);
    }
}
