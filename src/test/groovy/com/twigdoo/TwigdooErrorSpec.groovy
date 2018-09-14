package com.twigdoo

import com.fasterxml.jackson.databind.ObjectMapper
import com.twigdoo.util.ObjectMapperFactory
import spock.lang.Specification

class TwigdooErrorSpec extends Specification {

    private ObjectMapper mapper = ObjectMapperFactory.make();

    def "I can covert a JSON payload to the entity"() {
        given:
        String payload = '''
            {
              "error": "the error message"
            }
       '''

        when:
        TwigdooError entity = mapper.readValue(payload, TwigdooError);

        then:
        with(entity) {
            error == 'the error message'
        }
    }
}
