package org.seckill.dao.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dao.SecKillDao;
import org.seckill.entity.SecKill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by 单耀 on 2017/11/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RedisDaoTest {
    @Autowired
    private RedisDao redisDao;

    @Autowired
    private SecKillDao secKillDao;

    private long id = 1001L;

    @Test
    public void getSeckill() throws Exception {
        SecKill secKill = redisDao.getSeckill(id);
        if (secKill == null) {
            secKill = secKillDao.queryById(id);
            if (secKill != null) {
                String result = redisDao.putSeckill(secKill);
                System.out.println(secKill);
            }
        }
    }

    @Test
    public void putSeckill() throws Exception {

    }

}