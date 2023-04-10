// package com.vinjcent.api.config.thread;
//
// import com.alibaba.ttl.threadpool.TtlExecutors;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
//
// import java.util.concurrent.ArrayBlockingQueue;
// import java.util.concurrent.ExecutorService;
// import java.util.concurrent.ThreadPoolExecutor;
// import java.util.concurrent.TimeUnit;
// import java.util.concurrent.atomic.AtomicInteger;
//
// /**
//  * @author vinjcent
//  * @description 使用alibaba开源TTL解决父子线程共享问题
//  * @since 2023/3/30 17:42
//  */
// @Configuration
// public class AliThreadPoolConfiguration {
//
//     // ThreadLocal<String> transmittableThreadLocal = new TransmittableThreadLocal<>();
//
//     @Value("${spring.alibaba.task.execution.pool.core-size}")
//     private int corePoolSize;
//     @Value("${spring.alibaba.task.execution.pool.max-size}")
//     private int maxPoolSize;
//     @Value("${spring.alibaba.task.execution.pool.queue-capacity}")
//     private int queueCapacity;
//     @Value("${spring.alibaba.task.execution.thread-name-prefix}")
//     private String namePrefix;
//     @Value("${spring.alibaba.task.execution.pool.keep-alive}")
//     private int keepAliveSeconds;
//
//     @Bean("aliExecutorService")
//     public ExecutorService threadPoolTaskExecutorInit() {
//
//         AtomicInteger threadNumber = new AtomicInteger(1);
//
//         ThreadPoolExecutor executor = new ThreadPoolExecutor(
//                 // 核心线程数
//                 corePoolSize,
//                 // 最大线程数
//                 maxPoolSize,
//                 // 空闲线程存活时间
//                 keepAliveSeconds,
//                 // 空闲线程存活时间单位
//                 TimeUnit.SECONDS,
//                 // 工作队列(使用数组型阻塞队列)
//                 new ArrayBlockingQueue<>(queueCapacity),
//                 r -> {
//                     // 为工厂创建的每一个线程的格式化名字 threadName-No.[0,1,2,3...]
//                     return new Thread(r, namePrefix + threadNumber.getAndIncrement());
//                 }
//         );
//
//         /**
//          * 拒绝处理策略
//          * CallerRunsPolicy(): 由调用线程处理该任务，比如 main 线程
//          * AbortPolicy(): 丢弃任务并抛出 RejectedExecutionException 异常
//          * DiscardPolicy(): 丢弃任务，但是不抛出异常。可能导致无法发现系统的异常状态
//          * DiscardOldestPolicy(): 丢弃队列最前面的任务，然后重新提交被拒绝的任务
//          */
//         executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
//
//         // 将普通的线程池封装成TTL线程池
//         return TtlExecutors.getTtlExecutorService(executor);
//     }
// }
