package com.swust.chargingmobileback.config.exception;

/**
 * @author 73559
 * @Date 2018/7/8 16:11
 */
public class NullJsonObjectException extends RuntimeException {

    public NullJsonObjectException() {
        super();
    }

    /**
     *
     * @param message the reason for the exception
     */
    public NullJsonObjectException(String message) {
        super(message);
    }

}
