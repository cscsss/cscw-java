package com.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @Author: csc
 * @Date: 2020/6/30
 */
public class TwinsLock implements Lock {

    private final Sync sync = new Sync(2);
    @Override
    public void lockInterruptibly() throws InterruptedException {  throw new RuntimeException("");  }
    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {  throw new RuntimeException("");  }
    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
    @Override
    public void lock() {
        sync.acquireShared(1);
    }

    @Override
    public void unlock() {
        sync.releaseShared(1);
    }// 其他接口方法略 }
    @Override
    public boolean tryLock() {
        return sync.tryAcquireShared(1) > -1;
    }

    private static final class Sync extends AbstractQueuedSynchronizer {

        Sync(int count) {
            if (count <= 0) {
                throw new IllegalArgumentException("count must large than zero.");
            }
            setState(count);
        }
        @Override
        public int tryAcquireShared(int reduceCount) {
            for (; ; ) {
                int current = getState();
                int newCount = current - reduceCount;
                if (newCount < 0 || compareAndSetState(current, newCount)) {
                    return newCount;
                }
            }
        }
        @Override
        public boolean tryReleaseShared(int returnCount) {
            for (; ; ) {
                int current = getState();
                int newCount = current + returnCount;
                if (compareAndSetState(current, newCount)) {
                    return true;
                }
            }
        }
        public Condition newCondition() {
            return new ConditionObject();
        }
    }
}
