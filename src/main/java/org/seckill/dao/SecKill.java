package org.seckill.dao;

import java.util.Date;
import java.util.List;

/**
 * Created by 单耀 on 2017/10/31.
 */
public interface SecKill {

    /**
     * 减库存
     * @param seckillId
     * @param killTime
     * @return 如果返回结果大于1，表示更新行数大于1
     */
    int reduceNumber(long seckillId, Date killTime);

    /**
     * 根据Id查询秒杀对象
     * @param seckillId
     * @return
     */
    SecKill queryById(long seckillId);

    /**
     * 根据偏移量查询列表
     * @param offet
     * @param limit
     * @return
     */
    List<SecKill> queryAll(int offet, int limit);
}
