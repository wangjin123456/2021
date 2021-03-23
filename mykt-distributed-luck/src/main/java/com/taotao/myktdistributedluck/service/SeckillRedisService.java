package com.taotao.myktdistributedluck.service;


import com.taotao.myktdistributedluck.entity.CommodityDetails;
import com.taotao.myktdistributedluck.mapper.CommodityDetailsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName SeckillRedisService
 * @Author 蚂蚁课堂余胜军 QQ644064779 www.mayikt.com
 * @Version V1.0
 **/
@Slf4j
@RestController
public class SeckillRedisService {
    @Autowired
    private CommodityDetailsMapper commodityDetailsMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private String redisLockKey = "mayiktLock";
    @Autowired
    private RedisLockImpl redisLockImpl;
    //    /**
//     * redis实现分布式锁  idea调试 停止 属于正常停止 idea非调试的情况下 非正常停止
//     *
//     * @return
//     * @throws Exception
////     */
//    @RequestMapping("/seckilRedisLock")
//    public String seckilRedisLock(Long commodityId) throws Exception {
//        try {
//            // setnx
//            Boolean lock = stringRedisTemplate.opsForValue().setIfAbsent(redisLockKey, "1", 30L, TimeUnit.SECONDS);
//            if (!lock) {
//                log.info("<获取锁失败>");
//                return "获取失败啦，请稍后重试!";
//
//            }
//            Thread.sleep(10000000);
//            CommodityDetails commodityDetails = commodityDetailsMapper.getCommodityDetails(commodityId);
//            Long stock = commodityDetails.getStock();
//            if (stock > 0) {
//                log.info("<开始执行扣库存..>");
//                int result = commodityDetailsMapper.reduceInventory(1l);
//                return result > 0 ? "扣库存成功" : "扣库存失败";
//            }
//            // 扣库存失败
////            log.info("<扣库存失败>");
//
//        } catch (Exception e) {
//            log.error("<e:>", e);
//        } finally {
//            // 释放锁
//            log.info("<释放锁>");
//            stringRedisTemplate.delete(redisLockKey);
//        }
//        return "fail";
//    }

    //    /**
//     * redis实现分布式锁
//     *
//     * @return
//     * @throws Exception
//     */
    @RequestMapping("/seckilRedisLock")
    public String seckilRedisLock(Long commodityId) throws Exception {
        try {
            // 获取锁
            boolean getLock = redisLockImpl.tryLock();
            if (!getLock) {
                return "获取锁失败，请稍后重试!";
            }
            Thread.sleep(300);
            CommodityDetails commodityDetails = commodityDetailsMapper.getCommodityDetails(commodityId);
            Long stock = commodityDetails.getStock();
            if (stock > 0) {
                log.info("<开始执行扣库存..>");
                int result = commodityDetailsMapper.reduceInventory(1L);
                return result > 0 ? "扣库存成功" : "扣库存失败";
            }
            // 扣库存失败
            log.info("<扣库存失败>");
        } catch (Exception e) {
            log.error("<e:>", e);
        } finally {
            // 释放锁
            redisLockImpl.releaseLock();
        }
        return "fail";
    }

//    /**
//     * //     * redis实现分布式锁
//     * //     *
//     * //     * @return
//     * //     * @throws Exception
//     * //
//     */
//    @RequestMapping("/seckilRedisLock")
//    public String seckilRedisLock(Long commodityId) throws Exception {
//        try {
//            return seckilLockA(commodityId);
//        } catch (Exception e) {
//            log.error("<e:>", e);
//            return "fail";
//        } finally {
//            // 释放锁
//            redisLockImpl.releaseLock();
//        }
//    }
//
//    private String seckilLockA(Long commodityId) {
//        // 获取锁
//        boolean getLock = redisLockImpl.tryLock();
//        if (!getLock) {
//            return "获取锁失败，请稍后重试!";
//        }
//        CommodityDetails commodityDetails = commodityDetailsMapper.getCommodityDetails(commodityId);
//        return seckilLockB(commodityDetails);
//    }
//
//    private String seckilLockB(CommodityDetails commodityDetails) {
//        boolean getLock = redisLockImpl.tryLock();
//        if (!getLock) {
//            return "获取锁失败，请稍后重试!";
//        }
//
//        Long stock = commodityDetails.getStock();
//        if (stock > 0) {
//            log.info("<开始执行扣库存..>");
//            int result = commodityDetailsMapper.reduceInventory(1l);
//            return result > 0 ? "扣库存成功" : "扣库存失败";
//        }
//        // 扣库存失败
//        log.info("<扣库存失败>");
//        return "fail";
//    }


}
