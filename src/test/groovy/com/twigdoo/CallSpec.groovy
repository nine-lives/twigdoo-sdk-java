package com.twigdoo

import com.fasterxml.jackson.databind.ObjectMapper
import com.twigdoo.util.ObjectMapperFactory
import spock.lang.Specification

class CallSpec extends Specification {
    private ObjectMapper mapper = ObjectMapperFactory.make();

    def "I can covert a JSON payload to the entity"() {
        given:
        String payload = '''
                {
                }
       '''

        when:
        Call entity = mapper.readValue(payload, Call);

        then:
        true
    }
}