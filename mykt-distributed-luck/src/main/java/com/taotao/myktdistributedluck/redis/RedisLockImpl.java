
package com.taotao.myktdistributedluck.redis;

import com.taotao.myktdistributedluck.entity.RedisLockInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
*@title: RedisLockImpl
*@description； 项目
*@author wangjin
*@date 2021/3/23 22:11
*/  
@Component
@Slf4j
public class RedisLockImpl  implements  RedisLock{
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private String redisLockKey="mayiktlock";
    //重试时间
    private long timeout=3000L;
    /**
     * 缓存redis锁
     */
     private static Map<Thread, RedisLockInfo> lockInfoMap=new ConcurrentHashMap<>();
    @Override
    public boolean tryLock() {
        long start=System.currentTimeMillis();
        RedisLockInfo redisLockInfo=lockInfoMap.get(Thread.currentThread());
        if(redisLockInfo!=null && redisLockInfo.isState()){
            //这把锁可重入次数加1

            log.info("<获取锁成功 锁可直接重入>");
            return true;
        }
        for (;;){
            Boolean lock=stringRedisTemplate.opsForValue().setIfAbsent(redisLockKey,"1",
                    30L, TimeUnit.SECONDS );
            if(lock){
                log.info("<获取锁成功>");
                lockInfoMap.put(Thread.currentThread(),new RedisLockInfo());

                return true;
            }
            //控制一个超时时间
             long endtime=System.currentTimeMillis();
               if(endtime-start>timeout){
                   log.info("重试时间已经过了，不能再重复");
                   return  false;
               }
            //继续循环
            try {
                Thread.sleep(200);
            }catch (Exception e){
                e.getCause();
            }
        }
        //1多个jvm执行setnx命令，只有有个可以执行成功


    }

    @Override
    public boolean releaseLock() {
        stringRedisTemplate.delete(redisLockKey);
        return true;
    }
}
