package org.seckill.service.Impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.BaseTest;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.SecKill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SecKillException;
import org.seckill.exception.SeckillClosedException;
import org.seckill.service.ISecKillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.Executor;

import static org.junit.Assert.*;

/**
 * Created by 单耀 on 2017/11/6.
 */
public class SecKillServiceImplTest extends BaseTest{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ISecKillService secKillService;

    @Test
    public void getSeckillList() throws Exception {

        List list = secKillService.getSeckillList();
        logger.info("lsit={}",list);
    }

    @Test
    public void getById() throws Exception {
        long id = 1000L;
        SecKill secKill = secKillService.getById(id);
        logger.info("seckill={}",secKill);
    }

    @Test
    public void exportSeckillUrl() throws Exception {
        long id = 1000L;
        Exposer exposer = secKillService.exportSeckillUrl(id);
        logger.info("exposer:={}",exposer);
        //INFO  o.s.s.Impl.SecKillServiceImplTest - exposer:=Exposer{exposed=true, md5='c7cc31a53355e85656c3720bff596344', seckillId=1000, now=0, start=0, end=0}

    }

    @Test
    public void executeSeckill() throws Exception {
        long id = 1000L;
        long phone = 15121175063L;
        String md5 = "c7cc31a53355e85656c3720bff596344";
        try {
            SeckillExecution secKillException = secKillService.executeSeckill(id,phone,md5);
            logger.info("result={}",secKillException.getStateInfo());
        } catch (RepeatKillException e) {
            logger.error(e.getMessage());
        } catch (SeckillClosedException e){
            logger.error(e.getMessage());
        }
    }

    @Test
    public void executeLogic() {
        long id = 1001L;
        Exposer exposer = secKillService.exportSeckillUrl(id);

        if (exposer.isExposed()) {
            logger.info("exposer:={}",exposer);
            long phone = 15121175063L;
            String md5 = exposer.getMd5();
            try {
                SeckillExecution secKillException = secKillService.executeSeckill(id,phone,md5);
                logger.info("result={}",secKillException.getStateInfo());
            } catch (RepeatKillException e) {
                logger.error(e.getMessage());
            } catch (SeckillClosedException e){
                logger.error(e.getMessage());
            }
        } else {
            //秒杀结束
            logger.warn("exposer={}",exposer);
        }
    }

}