package org.seckill.exception;

/**
 * Created by 单耀 on 2017/11/5.
 */
public class SecKillException extends RuntimeException {

    public SecKillException(String message) {
        super(message);
    }

    public SecKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
