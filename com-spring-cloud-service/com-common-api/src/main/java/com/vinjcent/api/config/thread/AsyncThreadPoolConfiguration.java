// package com.vinjcent.api.config.thread;
//
// import com.vinjcent.api.domain.ThreadLocalData;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.core.task.TaskDecorator;
// import org.springframework.scheduling.annotation.AsyncConfigurer;
// import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
// import org.springframework.web.context.request.RequestContextHolder;
// import org.springframework.web.context.request.ServletRequestAttributes;
//
// import javax.servlet.http.HttpServletRequest;
// import java.util.Objects;
// import java.util.concurrent.Executor;
// import java.util.concurrent.RejectedExecutionHandler;
//
// /**
//  * @author vinjcent
//  * @description 异步线程池配置类(解决线程上下文共享问题)
//  * @since 2023/3/30 16:30
//  */
// @Slf4j
// @Configuration
// public class AsyncThreadPoolConfiguration implements AsyncConfigurer {
//
//     @Value("${spring.async.task.execution.pool.core-size}")
//     private int corePoolSize;
//     @Value("${spring.async.task.execution.pool.max-size}")
//     private int maxPoolSize;
//     @Value("${spring.async.task.execution.pool.queue-capacity}")
//     private int queueCapacity;
//     @Value("${spring.async.task.execution.thread-name-prefix}")
//     private String namePrefix;
//     @Value("${spring.async.task.execution.pool.keep-alive}")
//     private int keepAliveSeconds;
//
//     @Bean("asyncExecutorService")
//     @Override
//     public Executor getAsyncExecutor() {
//
//         ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//         executor.setCorePoolSize(corePoolSize);
//         executor.setMaxPoolSize(maxPoolSize);
//         executor.setKeepAliveSeconds(keepAliveSeconds);
//         executor.setThreadNamePrefix(namePrefix);
//         executor.setQueueCapacity(queueCapacity);
//         // 设置线程装饰器，解决父子线程之间上下文无法传递问题
//         executor.setTaskDecorator(new ContextTaskDecorator());
//
//
//         /**
//          * 拒绝处理策略
//          * CallerRunsPolicy(): 由调用线程处理该任务，比如 main 线程
//          * AbortPolicy(): 丢弃任务并抛出 RejectedExecutionException 异常
//          * DiscardPolicy(): 丢弃任务，但是不抛出异常。可能导致无法发现系统的异常状态
//          * DiscardOldestPolicy(): 丢弃队列最前面的任务，然后重新提交被拒绝的任务
//          */
//         executor.setRejectedExecutionHandler(customizeRejectedExecutionHandler());
//
//         // 初始化线程池
//         executor.initialize();
//         return executor;
//     }
//
//     /**
//      * 线程运行时抛出异常处理逻辑
//      *
//      * @return 函数式接口
//      */
//     @Override
//     public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
//         return (throwable, method, objects) -> {
//             log.error("====================捕获线程异常=================");
//             log.error("错误信息:{}", throwable.getMessage());
//             log.error("调用的方法:{}", method.getName());
//             log.error("参数列表:{}", objects);
//             log.error("===============================================");
//         };
//     }
//
//     /**
//      * 自定义线程池拒绝策略
//      */
//     private RejectedExecutionHandler customizeRejectedExecutionHandler() {
//         return (r, executor) -> {
//             if (!executor.isShutdown()) {
//                 try {
//                     log.info("线程池已满,线程进入休眠");
//                     // 线程休眠5秒
//                     Thread.sleep(5000);
//                 } catch (InterruptedException e) {
//                     log.error(e.getMessage());
//                 }
//                 // 再尝试入队
//                 executor.execute(r);
//             }
//         };
//     }
//
//     /**
//      * 内部类，解决父子线程之间上下文（例如SecurityContext，ServletRequestAttributes等）不能共享问题
//      */
//     static class ContextTaskDecorator implements TaskDecorator {
//         @Override
//         public Runnable decorate(Runnable runnable) {
//             try {
//                 // 先拿到主线程的上下文对象
//                 // SecurityContext securityContext = SecurityContextHolder.getContext();
//                 // 当父线程结束,子线程将无法获取父线程信息
//                 // ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//                 // 获取父线程的request的token(示例)
//                 HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
//                 String token = request.getHeader("token");
//                 return () -> {
//                     try {
//                         // 在子线程中重新设置进去
//                         // SecurityContextHolder.setContext(securityContext);
//                         // RequestContextHolder.setRequestAttributes(servletRequestAttributes, true);
//
//                         // 将父线程的token设置进子线程里
//                         ThreadLocalData.setToken(token);
//                         // 子线程方法执行
//                         runnable.run();
//                     } finally {
//                         // 使用完成之后清除子线程中的上下文,避免内存泄露
//                         // SecurityContextHolder.clearContext();
//                         RequestContextHolder.resetRequestAttributes();
//                     }
//                 };
//             } catch (IllegalStateException e) {
//                 return runnable;
//             }
//         }
//     }
// }
