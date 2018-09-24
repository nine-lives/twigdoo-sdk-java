package com.twigdoo

import com.fasterxml.jackson.databind.ObjectMapper
import com.twigdoo.util.ObjectMapperFactory
import org.joda.time.DateTime
import spock.lang.Specification

class CallSpec extends Specification {
    private ObjectMapper mapper = ObjectMapperFactory.make()

    def "I can covert a JSON payload to the entity"() {
        given:
        String payload = '''
                {
                   "call_type" : "incoming",
                   "contact_number" : "07411888888",
                   "id" : 1,
                   "lead_id" : 301,
                   "duration" : 123,
                   "_links" : {
                      "self" : "http://localhost:5000/lead/301/calls/1",
                      "lead" : "http://localhost:5000/lead/301"
                   },
                   "call_time" : "2018-09-19T15:35:53.549593+00:00",
                   "notes" : "notes here"
                 }
       '''

        when:
        Call entity = mapper.readValue(payload, Call);

        then:
        entity.callType == CallType.incoming
        entity.contactNumber == '07411888888'
        entity.id == 1
        entity.leadId == 301
        entity.duration == 123
        entity.callTime == DateTime.parse("2018-09-19T15:35:53.549593+00:00")
        entity.notes == 'notes here'
        entity.links.size() == 2
        entity.links['self'] == "http://localhost:5000/lead/301/calls/1"
        entity.links['lead'] == "http://localhost:5000/lead/301"
    }
}