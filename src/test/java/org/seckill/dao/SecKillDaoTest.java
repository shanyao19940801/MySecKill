package org.seckill.dao;

import com.sun.org.apache.xpath.internal.SourceTree;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SecKill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by 单耀 on 2017/11/2.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SecKillDaoTest {
    @Resource
    private SecKillDao secKillDao;
    @Test
    public void reduceNumber() throws Exception {
        Date date = new Date();
        int count  = secKillDao.reduceNumber(1000L,date);
        System.out.println(count);
    }

    @Test
    public void queryById() throws Exception {
        long id = 1000;
        SecKill secKill = secKillDao.queryById(id);
        System.out.println(secKill.getName());
        System.out.println(secKill);

    }

    @Test
    public void queryAll() throws Exception {
        List<SecKill> list = secKillDao.queryAll(0,100);
        for(SecKill secKill : list){
            System.out.println(secKill);
        }

    }

}