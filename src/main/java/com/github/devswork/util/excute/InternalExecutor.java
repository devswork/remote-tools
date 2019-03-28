package com.github.devswork.util.excute;

import com.github.devswork.util.excute.exception.InternalExecutorException;

public abstract class InternalExecutor<T> {

    int retryTimes = 500;

    int maxRetryCount = 3;

    public abstract T doRequest();

    public InternalExecutor() {
        super();
    }

    public InternalExecutor(int retryTimes, int maxRetryCount) {
        super();
        this.retryTimes = retryTimes;
        this.maxRetryCount = maxRetryCount;
    }

    public T run() {
        for (int i = 1; i <= maxRetryCount + 1; i++) {
            try {
                return this.doRequest();
            } catch (InternalExecutorException e) {
                if (i >= maxRetryCount + 1) {
                    throw new RuntimeException(e.toString());
                }
                int sleepMillis = retryTimes * (i - 1);
                if (sleepMillis > 0) {
                    try {
                        Thread.sleep(sleepMillis);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
        return null;
    }
}
