package com.artikon90.testtaskforalfabank.exception;

public class CurrencyNotFoundException extends RuntimeException {
    public CurrencyNotFoundException() {
        super("Currency not found. Retry your input.");
    }
}
