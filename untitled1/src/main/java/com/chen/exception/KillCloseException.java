package com.chen.exception;

/**
 * 秒杀关闭异常
 */
public class KillCloseException extends SeckillException{
    public KillCloseException(String message) {
        super(message);
    }

    public KillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
