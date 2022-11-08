package com.vinjcent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

//@EnableAspectJAutoProxy(exposeProxy = true)
@SpringBootApplication
public class RedisDistributedLockApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisDistributedLockApplication.class, args);
    }

    /**
     *         Long userId = 3035L;
     *         // 在使用 Long 封装类时,调用 toString() 方法会new一个对象,这样会导致 synchronized
     *         // 即使锁的是同一个用户,但是使用 toString() 方法会认为是不同对象,导致锁同一个用户不成功的情况
     *         // 使用 intern() 函数,会从 pool 池中查找,这样即使同一个用户也不会出现锁不成功的情况
     *         synchronized (userId.toString().intern()) {
     *         // 获取 serviceImpl 的代理对象(在 service 中,如果使用本身 @Service 类里的方法进行调用,会使得我们的 Spring AOP 的 JDK 动态代理失效,导致业务的事务也就失效)
     *             Object proxy = AopContext.currentProxy();   // 需要强转为service接口
     *             proxy.createOrder();    // createOrder() 是一个被 Spring 事务接管的方法
     *         }
     *         【注意】需要在主启动类上添加@EnableAspectJAutoProxy(exposeProxy = true)来暴露我们的代理类,必须在被声明为bean中使用,不然会报错
     */

}
