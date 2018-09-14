package com.twigdoo.util;

import java.time.Clock;
import java.util.concurrent.TimeUnit;

public class RateLimiter {
    private final int requestBurstSize;
    private final long burstTimeLimit;
    private long lastBurstStartTime;
    private int requestsInBurst;

    public RateLimiter(int requestsPerSecond, int requestBurstSize) {
        this.requestBurstSize = requestBurstSize;
        this.burstTimeLimit = TimeUnit.SECONDS.toMillis(requestBurstSize / requestsPerSecond);
    }

    public void blockTillRateLimitReset() {
        if (getRequestLeftInBurst() <= 0) {
            long blockFor = getMillisTillNextBurstWindow();
            if (blockFor > 0) {
                synchronized (this) {
                    try {
                        this.wait(blockFor);
                    } catch (InterruptedException ignore) {
                    }
                }
            }
            reset();
        }
        requestsInBurst++;
    }

    private int getRequestLeftInBurst() {
        return requestBurstSize - requestsInBurst;
    }

    private long getMillisTillNextBurstWindow() {
        long nextBurstStartTime = lastBurstStartTime + burstTimeLimit;
        return nextBurstStartTime - Clock.systemUTC().millis();
    }

    private void reset() {
        lastBurstStartTime = 0;
        requestsInBurst = 0;
    }
}
