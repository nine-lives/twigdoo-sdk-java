package com.twigdoo

import com.fasterxml.jackson.databind.ObjectMapper
import com.twigdoo.util.ObjectMapperFactory
import spock.lang.Specification

class TwigdooErrorDeserialiserSpec extends Specification {

    private ObjectMapper mapper = ObjectMapperFactory.make();

    def "I can covert a JSON payload to the entity"() {
        given:
        String payload = '''
            {
              "error": {
                "message": "Invalid request body", 
                "message_code": 400, 
                "errors": {
                    "invalid": [".client.email", ".client.name"],
                    "missing": [".service.address", ".service.name"]
                }
              }
            }
       '''

        when:
        TwigdooErrorDeserialiser entity = mapper.readValue(payload, TwigdooErrorDeserialiser);

        then:
        with(entity) {
            error.message == 'Invalid request body'
            error.messageCode == 400
            error.errors.invalid == [".client.email", ".client.name"]
            error.errors.missing == [".service.address", ".service.name"]
        }
    }
}
