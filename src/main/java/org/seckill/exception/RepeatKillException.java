package org.seckill.exception;

import org.seckill.dto.SeckillExecution;

/**
 * Created by 单耀 on 2017/11/5.
 * 重复秒杀异常（运行起异常）
 */
public class RepeatKillException extends SecKillException {
    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
