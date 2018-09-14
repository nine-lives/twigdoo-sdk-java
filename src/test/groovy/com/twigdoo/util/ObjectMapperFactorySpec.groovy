package com.twigdoo.util

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification

class ObjectMapperFactorySpec extends Specification {

    def "I can configure whether fail on unknown property is set"() {
        when:
        ObjectMapperFactory.setFailOnUnknownProperties(true)
        ObjectMapper mapper = ObjectMapperFactory.make()

        then:
        mapper.deserializationConfig.hasDeserializationFeatures(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES.getMask())

        when:
        ObjectMapperFactory.setFailOnUnknownProperties(false)
        mapper = ObjectMapperFactory.make()

        then:
        !mapper.deserializationConfig.hasDeserializationFeatures(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES.getMask())
    }

    def "The constructor is private"() {
        when:
        ObjectMapperFactory factory = new ObjectMapperFactory()

        then:
        factory
    }

}
