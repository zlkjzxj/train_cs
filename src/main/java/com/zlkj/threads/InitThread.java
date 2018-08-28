package com.zlkj.threads;


import com.zlkj.util.CheckEquType;

public class InitThread {
    //开启线程池
    public static HandlerThreadsPool createThreadPool() {
        ThreadsPoolConfig config = new ThreadsPoolConfig();
        config.setCustom(true);
        config.setCorePoolSize(20);
        config.setMaximumPoolSize(25);
        // 初始化 线程池
        HandlerThreadsPool handlerThreadsPool = new HandlerThreadsPool(config);
        return handlerThreadsPool;
    }
}
