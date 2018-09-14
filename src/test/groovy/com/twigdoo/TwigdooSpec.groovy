package com.twigdoo

import spock.lang.Specification

class TwigdooSpec extends Specification {

    def "I can use the auth key constructor to create the api"() {
        when:
        Twigdoo api = Twigdoo.make('apiKey')

        then:
        Configuration config = api.client.configuration;
        config.apiKey == 'apiKey'
        config.endpoint == 'https://api.twigdoo.com'
        config.maxConnectionsPerRoute == 20
        config.requestBurstSize == 20
        config.requestsPerSecond == 5
        !config.blockTillRateLimitReset
    }

    def "I can use the configuration object to create the api"() {
        given:
        Configuration config = new Configuration()
                .withApiKey("secret")
                .withEndpoint("https://bpi.twigdoo.com")

        when:
        Twigdoo api = Twigdoo.make(config)

        then:
        config == api.client.configuration;
        config.endpoint == "https://bpi.twigdoo.com"
    }
}
