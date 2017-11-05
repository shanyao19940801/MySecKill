package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by 单耀 on 2017/11/2.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {
    @Resource
    private SuccessKilledDao successKilledDao;
    @Test
    public void insertSuccessSilled() throws Exception {
        long id = 1000L;
        long phone = 123423423L;
        int count  = successKilledDao.insertSuccessKilled(id,phone);
        System.out.println(count);
    }


 /*   @Test
    public void queryByIdWithSecKill() throws Exception {
        long id = 1000L;
        long phone = 123423423L;
        SuccessKilled successKilled = successKilledDao.queryByIdWithSecKill(id,phone);
        System.out.println(successKilled);
        System.out.println(successKilled.getSecKill());
    }*/

    @Test
    public void queryByIdWithSecKill(){
        long id = 1000L;
        long phone = 123423423L;
        SuccessKilled successKilled = successKilledDao.queryByIdWithSecKill(id,phone);
        System.out.println(successKilled);
    }
}