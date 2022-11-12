package com.vinjcent.service;

public interface Lock {

    // 获取锁
    public boolean tryLock(long timeout);

    // 释放锁
    public void unLock();

}
