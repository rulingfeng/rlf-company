package com.rlf.config;

import feign.RetryableException;
import feign.Retryer;

import java.util.concurrent.TimeUnit;

/**
 * @author rulingfeng
 * @time 2022/11/28 09:29
 * @desc
 */
public class RetryerGoods implements Retryer {
    private final int maxAttempts;
    private final long period;
    private final long maxPeriod;
    int attempt;
    long sleptForMillis;

    public RetryerGoods() {
        this(100L, TimeUnit.SECONDS.toMillis(1L), 1);
    }

    public RetryerGoods(long period, long maxPeriod, int maxAttempts) {
        this.period = period;
        this.maxPeriod = maxPeriod;
        this.maxAttempts = maxAttempts;
        this.attempt = 1;
    }

    protected long currentTimeMillis() {
        return System.currentTimeMillis();
    }
    @Override
    public void continueOrPropagate(RetryableException e) {
        if (this.attempt++ >= this.maxAttempts) {
            throw e;
        } else {
            long interval;
            if (e.retryAfter() != null) {
                interval = e.retryAfter().getTime() - this.currentTimeMillis();
                if (interval > this.maxPeriod) {
                    interval = this.maxPeriod;
                }

                if (interval < 0L) {
                    return;
                }
            } else {
                interval = this.nextMaxInterval();
            }

            try {
                Thread.sleep(interval);
            } catch (InterruptedException var5) {
                Thread.currentThread().interrupt();
                throw e;
            }

            this.sleptForMillis += interval;
        }
    }


    long nextMaxInterval() {
        long interval = (long)((double)this.period * Math.pow(1.5, (double)(this.attempt - 1)));
        return interval > this.maxPeriod ? this.maxPeriod : interval;
    }

    @Override
    public Retryer clone() {
        return new Default(this.period, this.maxPeriod, this.maxAttempts);
    }
}

