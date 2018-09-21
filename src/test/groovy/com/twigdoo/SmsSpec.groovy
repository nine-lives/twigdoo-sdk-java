package com.twigdoo

import com.fasterxml.jackson.databind.ObjectMapper
import com.twigdoo.util.ObjectMapperFactory
import org.joda.time.DateTime
import spock.lang.Specification

class SmsSpec extends Specification {
    private ObjectMapper mapper = ObjectMapperFactory.make()

    def "I can covert a JSON payload to the entity"() {
        given:
        String payload = '''
                {
                   "lead_id" : 301,
                   "sms_type" : "incoming",
                   "sms_time" : "2018-09-19T15:34:54.131120+00:00",
                   "id" : 1,
                   "contact_number" : "07411123123",
                   "content" : "Hello",
                   "links" : {
                      "lead" : "http://localhost:5000/lead/301",
                      "self" : "http://localhost:5000/lead/301/smses/1"
                   }
                }
       '''

        when:
        Sms entity = mapper.readValue(payload, Sms);

        then:
        entity.smsType == SmsType.incoming
        entity.contactNumber == '07411123123'
        entity.id == 1
        entity.leadId == 301
        entity.smsTime == DateTime.parse("2018-09-19T15:34:54.131120+00:00")
        entity.content == 'Hello'
        entity.links.size() == 2
        entity.links['self'] == "http://localhost:5000/lead/301/smses/1"
        entity.links['lead'] == "http://localhost:5000/lead/301"
    }
}