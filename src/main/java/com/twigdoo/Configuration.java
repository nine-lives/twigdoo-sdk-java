package com.twigdoo;

import java.io.IOException;
import java.util.Properties;

public class Configuration {
    private static final int DEFAULT_MAX_CONNECTIONS_PER_ROUTE = 20;
    private static final int DEFAULT_REQUESTS_PER_SECOND = 5;
    private static final int DEFAULT_REQUEST_BURST_SIZE = 20;

    private final String sdkUserAgent;
    private String userAgent;
    private String endpoint = "https://api.twigdoo.com";
    private String apiKey;
    private int maxConnectionsPerRoute = DEFAULT_MAX_CONNECTIONS_PER_ROUTE;
    private boolean blockTillRateLimitReset;
    private int requestsPerSecond = DEFAULT_REQUESTS_PER_SECOND;
    private int requestBurstSize = DEFAULT_REQUEST_BURST_SIZE;

    public Configuration() {
        sdkUserAgent = "twigdoo-java-sdk/" + getVersion();
        userAgent = sdkUserAgent;
    }

    public String getEndpoint() {
        return endpoint;
    }

    /**
     * Set the base api url. Defaults to https://api.twigdoo.com
     *
     * @param endpoint the base api url
     * @return this instance
     */
    public Configuration withEndpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    /**
     * Get the api key id
     *
     * @return the api key
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * Set the api key used for authentication. Required.
     *
     * @param apiKey the api  key
     * @return this instance
     */
    public Configuration withApiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    /**
     * Get the max connections per route
     *
     * @return the max connections per route
     */
    public int getMaxConnectionsPerRoute() {
        return maxConnectionsPerRoute;
    }

    /**
     * Set the effective maximum number of concurrent connections in the pool. Connections try to make use of the
     * keep-alive directive. Defaults to 20
     *
     * @param maxConnectionsPerRoute the max connections per router
     * @return this instance
     */
    public Configuration withMaxConnectionsPerRoute(int maxConnectionsPerRoute) {
        this.maxConnectionsPerRoute = maxConnectionsPerRoute;
        return this;
    }

    /**
     * Get the user agent string being to send in the request headers
     *
     * @return the user agent string
     */
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * Set the user agent string sent in the request
     *
     * @param userAgent the user agent string
     * @return this instance
     */
    public Configuration withUserAgent(String userAgent) {
        if (userAgent == null || userAgent.trim().isEmpty()) {
            this.userAgent = sdkUserAgent;
        } else {
            this.userAgent = userAgent.trim() + " " + sdkUserAgent;
        }
        return this;
    }

    /**
     * Will the client block until the rate limit reset window is reached if the rate limit is set
     *
     * @return true if it will block
     */
    public boolean isBlockTillRateLimitReset() {
        return blockTillRateLimitReset;
    }

    /**
     * Set whether the client should block if the rate limit has been reached until the reset timestamp has
     * elapsed. Defaults to false
     *
     * @param blockTillRateLimitReset true to block, false otherwise
     * @return this instance
     */
    public Configuration withBlockTillRateLimitReset(boolean blockTillRateLimitReset) {
        this.blockTillRateLimitReset = blockTillRateLimitReset;
        return this;
    }


    /**
     * Get the configured requests per second for the account. Defaults to 5.
     * @return requests per second
     */
    public int getRequestsPerSecond() {
        return requestsPerSecond;
    }

    /**
     * Set the configured requests per second for the account. Defaults to 5.
     * @param requestsPerSecond requests per second
     * @return this instance
     */
    public Configuration withRequestsPerSecond(int requestsPerSecond) {
        this.requestsPerSecond = requestsPerSecond;
        return this;
    }

    /**
     * Get the configured burst size for requests. Defaults to 20.
     * @return requests per second
     */
    public int getRequestBurstSize() {
        return requestBurstSize;
    }

    /**
     * Set the configured burst size for requests. Defaults to 20.
     * @param requestBurstSize requests per second
     * @return this instance
     */
    public Configuration withRequestBurstSize(int requestBurstSize) {
        this.requestBurstSize = requestBurstSize;
        return this;
    }

    private String getVersion() {
        try {
            Properties versionProperties = new Properties();
            versionProperties.load(Configuration.class.getClassLoader().getResourceAsStream("version.properties"));
            return versionProperties.getProperty("version");
        } catch (IOException ignore) {
            return "1.x";
        }
    }
}
