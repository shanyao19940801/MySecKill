package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SecKill;

import java.util.Date;
import java.util.List;

/**
 * Created by 单耀 on 2017/10/31.
 */
public interface SecKillDao {

    /**
     * 减库存
     * @param seckillId
     * @param killTime
     * @return 如果返回结果大于1，表示更新行数大于1
     */
    int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);

    /**
     * 根据Id查询秒杀对象
     * @param seckillId
     * @return
     */
    SecKill queryById(long seckillId);

    /**
     * 根据偏移量查询列表
     * @param offset
     * @param limit
     * @return
     *
     */
    //z注意下面使用@Param的注解，因为java形参超过一个就会自动变成arg这种形式，这样当传到SecKillDao.xml文件中时将无法识别,就会出现下面的错误
    //Parameter 'offset' not found. Available parameters are [0, 1, param1, param2]

    List<SecKill> queryAll(@Param("offset") int offset, @Param("limit") int limit);
}
