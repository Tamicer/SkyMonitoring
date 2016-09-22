package com.tamic.statInterface.statsdk.service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Tamic on 2016-09-22.
 */
public class TcHandleThreadPool {

    private final static int POOL_SIZE = 4;
    private final static int MAX_POOL_SIZE = 6;
    private final static int KEEP_ALIVE_TIME = 4;
    private final Executor mExecutor;
    private final static String THREAD_NAME ="paf-stat-thread-pool";
    public TcHandleThreadPool() {

        ThreadFactory factory = new PriorityThreadFactory(THREAD_NAME, android.os.Process.THREAD_PRIORITY_BACKGROUND);
        BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<Runnable>();
        mExecutor = new ThreadPoolExecutor(POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, workQueue, factory);
    }

    public void execute(Runnable command){
        mExecutor.execute(command);
    }

    public Executor getExecutor() {
        return mExecutor;
    }
}
