package com.swust.chargingmobileback.config.exception;

/**
 * @author 73559
 * @Date 2018/7/8 16:42
 */
public class MoneyNotEnoughException extends RuntimeException{
    public MoneyNotEnoughException() {
        super();
    }
    /**
     * @param message the reason for the exception
     */
    public MoneyNotEnoughException(String message) {
        super(message);
    }
}
