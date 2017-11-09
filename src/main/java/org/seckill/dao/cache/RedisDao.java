package org.seckill.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.seckill.entity.SecKill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by 单耀 on 2017/11/9.
 */
public class RedisDao {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private RuntimeSchema<SecKill> schema = RuntimeSchema.createFrom(SecKill.class);
    private JedisPool jedisPool;

    public RedisDao(String ip, int port) {
        jedisPool = new JedisPool(ip,port);
    }

    public SecKill getSeckill (long seckillId) {
        Jedis jedis = jedisPool.getResource();
        //redis操作逻辑
        try {
            try {
                String key = "seckill:" + seckillId;
                //并没有实现内部序列化操作
                //get->byte[] -> 反序列化 ->Object(Seckill)
                //采用自定义序列化
                //采用第三方序列化工具protostuff : pojo，github上有
                byte[] bytes =  jedis.get(key.getBytes());
                //缓存重获取到
                if (bytes != null) {
                    //传入空对象
                    SecKill secKill = schema.newMessage();
                    ProtostuffIOUtil.mergeFrom(bytes, secKill, schema);
                    return secKill;
                }
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }

    public String putSeckill(SecKill secKill) {
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckillId" + secKill.getSeckillId();
                byte[] bytes = ProtostuffIOUtil.toByteArray(secKill, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                //超市缓存
                int timeOut = 60 * 60;
                String result = jedis.setex(key.getBytes(), timeOut, bytes);
                return result;
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }
}
