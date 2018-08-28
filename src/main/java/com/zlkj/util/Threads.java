package com.zlkj.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Threads {
    private ExecutorService executorService;

    public void createThreadPool(int poolSize) {
        //生成对应数量的线程池
        executorService = Executors.newFixedThreadPool(poolSize);
    }

    public void closeThreadPool(int poolSize) {
        if (executorService != null) {
            executorService.shutdown();//执行完毕关闭线程池
        }
    }
}
