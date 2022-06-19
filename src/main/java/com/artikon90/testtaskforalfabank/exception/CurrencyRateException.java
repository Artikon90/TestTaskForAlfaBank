package com.artikon90.testtaskforalfabank.exception;

public class CurrencyRateException extends RuntimeException {
    public CurrencyRateException() {
        super("Что-то пошло не так!");
    }
}
