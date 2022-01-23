package com.rbc.stocktickermanager.exception;

public class StockTickerException extends Exception{
    public StockTickerException() {
        super();
    }

    public StockTickerException(String msg) {
        super(msg);
    }

    public StockTickerException(String msg, Exception e) {
        super(msg, e);
    }
}
