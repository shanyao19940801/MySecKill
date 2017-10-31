package org.seckill.dao;

/**
 * Created by 单耀 on 2017/10/31.
 */
public interface SuccessSilled {

    /**
     * 插入购买明细，可过滤重复
     * @param seckillId
     * @param userPhone
     * @return
     */
    int insertSuccessSilled(long seckillId,long userPhone);

    /**
     * 根据id查询successkilled并携带秒杀产品对象实体
     * @param seckill
     * @return
     */
    SuccessSilled queryByIdWithSecKill(long  seckill);
}
