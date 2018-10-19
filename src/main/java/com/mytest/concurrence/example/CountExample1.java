package com.mytest.concurrence.example;

import com.mytest.concurrence.annotations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Slf4j
@NotThreadSafe
public class CountExample1 {
    public static final int clientTotal = 5000;
    private static final int threadTotal = 200;
    public static int count = 0;

    public static void main(String[] args) throws InterruptedException {
        //获取线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        //生成信号量
        final Semaphore semaphore = new Semaphore(threadTotal);
        //生成计数器
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        //加入请求
        for (int i = 0; i < clientTotal; i++){
            executorService.execute(() -> {//Runnable lambda
                try {
                    //请求，执行，释放
                    semaphore.acquire();
                    add();
                    semaphore.release();
                }catch (Exception e){
                    log.error("exception", e);
                }
                //计数-1
                countDownLatch.countDown();
            });
        }
        //在有请求没有执行完成之前等待
        countDownLatch.await();
        //关闭线程池
        executorService.shutdown();
        //在log中打印结果
        log.info("count:{}", count);
    }

    private static void add(){
        count ++;
    }
}
