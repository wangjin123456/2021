package com.taotao.myktdistributedluck;

import org.springframework.stereotype.Component;

/**
 * @ClassName LockContext
 * @Author 蚂蚁课堂余胜军 QQ644064779 www.mayikt.com
 * @Version V1.0
 **/
public class LockContext {

    private static ThreadLocal<String> threadLocal = new ThreadLocal<String>();

    public static void set(String lockNode) {
        threadLocal.set(lockNode);
    }

    public static String get() {
        return threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }
}
