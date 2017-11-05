package org.seckill.exception;

/**
 * Created by 单耀 on 2017/11/5.
 * 秒杀关闭异常
 */
public class SeckillClosedException extends SecKillException {
    public SeckillClosedException(String message) {
        super(message);
    }

    public SeckillClosedException(String message, Throwable cause) {
        super(message, cause);
    }
}
