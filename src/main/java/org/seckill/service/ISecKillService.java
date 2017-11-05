package org.seckill.service;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.SecKill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SecKillException;
import org.seckill.exception.SeckillClosedException;

import java.util.List;

/**
 * 业务接口：站在"使用者"角度设计接口 三个方面：方法定义粒度，参数，返回类型（return 类型/异常）
 *
 * @author 李奕锋
 */
public interface ISecKillService {

    /**
     * 查询所有秒杀记录
     *
     * @return
     */
    List<SecKill> getSeckillList();

    /**
     * 查询单个秒杀记录
     *
     * @param seckillId
     * @return
     */
    SecKill getById(long seckillId);

    /**
     * 秒杀开启时输出秒杀接口地址，否则输出系统时间和秒杀时间
     *
     * @param seckillId
     * @return
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀操作
     *
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     * @throws SecKillException
     * @throws RepeatKillException
     * @throws SeckillClosedException
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SecKillException, RepeatKillException, SeckillClosedException;

}