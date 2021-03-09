package com.taotao.myktdistributedluck.aop;//package com.mayikt.task.aop;
//
//import com.mayikt.task.LockInfo;
//import com.mayikt.task.MayiktTask;
//import com.mayikt.task.lock.impl.ZookeeperTemplateLock;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;
//
//@Component
//@Slf4j
//@Aspect
//public class ExtAsyncAop {
//
//
//    @Autowired
//    private ZookeeperTemplateLock zookeeperTemplateLock;
//    private static Map<String, LockInfo> lockService = new HashMap<>();
//    private static ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
//
//    public ExtAsyncAop() {
//        service.scheduleAtFixedRate(new DetectionAlgorithm(), 0, 5, TimeUnit.SECONDS);
//    }
//
//    /**
//     * 检测死锁
//     */
//    class DetectionAlgorithm implements Runnable {
//
//        @Override
//        public void run() {
//            lockService.forEach((k, lockInfo) -> {
//                if (lockInfo.getState().equals("start")) {
//                    // 将该session连接主动关闭~
//                    lockInfo.getLock().unlock();
//                    // 将该线程主动停止了
//                    lockInfo.getLockThread().interrupt();
//                    // 回滚事务
//                    // 移除本身自己
//                    lockService.remove(k);
//                }
//            });
//        }
//    }
//
//    @Around(value = "@annotation(com.mayikt.task.MayiktLock)")
//    public void doBefore(ProceedingJoinPoint joinPoint) throws Throwable {
//        try {
//            zookeeperTemplateLock.getLock();
//            String serviceId = UUID.randomUUID().toString();
//            lockService.put(serviceId, new LockInfo(serviceId, Thread.currentThread(), "start", zookeeperTemplateLock));
//            // 执行目标方法
//            joinPoint.proceed();
//            lockService.put(serviceId, new LockInfo(serviceId, Thread.currentThread(), "stop", zookeeperTemplateLock));
//            zookeeperTemplateLock.unlock();
//        } catch (Exception e) {
//            e.printStackTrace();
//            zookeeperTemplateLock.unlock();
//        }
//
//    }
//}