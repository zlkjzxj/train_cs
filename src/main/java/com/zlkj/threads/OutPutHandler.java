package com.zlkj.threads;


import com.zlkj.commons.Constant;
import com.zlkj.util.FileCopy;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


@Service
public class OutPutHandler implements Runnable {

//
//    @Autowired
//    private ThreadsPoolConfig config;  // 注入 配置

    private String disk;
    private ProgressBar progressBar;
    private Label label;

    public OutPutHandler(String disk, ProgressBar progressBar, Label label) {
        this.disk = disk;
        this.progressBar = progressBar;
        this.label = label;
    }

    @Override
    public void run() {
        // do 这里 写 处理的逻辑
        System.out.println("创建线程 处理事务....");
        System.out.println("this.disk:" + disk);
        FileCopy.copy(disk, Constant.WRITEFILETO, progressBar, label);
    }

    @PostConstruct
    public void loadThreadsPool() {
        ThreadsPoolConfig config = new ThreadsPoolConfig();
        config.setCustom(true);
        config.setCorePoolSize(20);
        config.setMaximumPoolSize(25);
        // 初始化 线程池
        HandlerThreadsPool handlerThreadsPool = new HandlerThreadsPool(config);
        //调用线程池，创建线程  。处理事件
        handlerThreadsPool.execute(new OutPutHandler(disk, progressBar, label));
    }
}