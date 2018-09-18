package com.twigdoo.util

import spock.lang.Specification

import java.time.Clock

class RateLimiterSpec extends Specification {

    def "I can rate limit"() {
        given:
        RateLimiter limiter = new RateLimiter(1, 5)
        long start = -System.currentTimeMillis()

        when:
        for (int i = 0; i < 4; ++i) {
            limiter.blockTillRateLimitReset()
        }
        long time = Clock.systemUTC().millis() + start

        then:
        limiter.requestLeftInBurst == 1
        time < 3000

        when:
        for (int i = 0; i < 4; ++i) {
            limiter.blockTillRateLimitReset()
        }
        time = Clock.systemUTC().millis() + start

        then:
        limiter.requestLeftInBurst == 2
        time > 5000
        time < 10000
    }
}
