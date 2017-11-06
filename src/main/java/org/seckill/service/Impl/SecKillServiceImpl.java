package org.seckill.service.Impl;

import org.seckill.dao.SecKillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.SecKill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStateEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SecKillException;
import org.seckill.exception.SeckillClosedException;
import org.seckill.service.ISecKillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by 单耀 on 2017/11/5.
 */
@Service
public class SecKillServiceImpl implements ISecKillService{


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SecKillDao secKillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

    //用于混淆md5
    private final String slat = "werqwr23afd^&$^(saf";

    public List<SecKill> getSeckillList() {
        return secKillDao.queryAll(0, 100);
    }

    public SecKill getById(long seckillId) {
        return secKillDao.queryById(seckillId);
    }

    public Exposer exportSeckillUrl(long seckillId) {
        SecKill secKill = secKillDao.queryById(seckillId);
        if (secKill == null) {
            return new Exposer(false, seckillId);
        }
        Date startTime = secKill.getStartTime();
        Date endTime = secKill.getEndTime();
        Date now = new Date();
        if ((now.getTime() < startTime.getTime()) || (now.getTime() > endTime.getTime())) {
            return new Exposer(false, seckillId, now.getTime(), startTime.getTime(), endTime.getTime());
        }
        String md5 = getMD5(seckillId);//TODO
        return new Exposer(true, md5, seckillId);
    }

    private String getMD5(long secKillId){
        String base = secKillId + "/" + slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    @Transactional
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SecKillException, RepeatKillException, SeckillClosedException {
        if(md5 == null || !md5.equals(getMD5(seckillId))){

            throw  new SecKillException("seckill is closed" );

        }
        try {
            //执行秒杀逻辑：减库存 + 购买行为
            Date nowTime = new Date();
            //减库存
            int update = secKillDao.reduceNumber(seckillId,nowTime);
            if (update <= 0) {
                //没有跟新到记录，秒杀结束
                throw new SeckillClosedException("repeate is closed");
            } else {
                //记录购买行为
                int insert = successKilledDao.insertSuccessKilled(seckillId, userPhone);
                if (insert <= 0) {
                    throw  new RepeatKillException("seckill repeat");
                } else {
                    //秒杀成功
                    SuccessKilled successKilled  = successKilledDao.queryByIdWithSecKill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successKilled);
                }
            }
        } catch (SeckillClosedException e1) {
            throw e1;
        } catch (RepeatKillException e2) {
            throw e2;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SecKillException("seckill inner error:" + e.getMessage());
        }
    }
}
