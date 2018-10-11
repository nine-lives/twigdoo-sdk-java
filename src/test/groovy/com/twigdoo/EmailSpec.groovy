package com.twigdoo

import com.fasterxml.jackson.databind.ObjectMapper
import com.twigdoo.util.ObjectMapperFactory
import org.joda.time.DateTime
import spock.lang.Specification

class EmailSpec extends Specification {
    private ObjectMapper mapper = ObjectMapperFactory.make()

    def "I can covert a JSON payload to the entity"() {
        given:
        String payload = '''
                 {  
                      "message_id" : "153477862836.8162.5467706423478211221@matt-ThinkPad-X1-Carbon-3rd",
                      "email_type" : "outgoing",
                      "summary_body" : "Hi friend",
                      "subject" : "Hello!",
                      "sent" : "2018-08-20T15:23:48+00:00",
                      "_links" : {
                         "self" : "http://localhost:5000/lead/238/emails/80",
                         "lead" : "http://localhost:5000/lead/238"
                      },
                      "id" : 80,
                      "to" : "Testing Matt <matt.wilson+test@twigdoo.com>",
                      "from" : "Matt Wilson <matt.wilson@twigdoo.com>",
                      "lead_id" : 238,
                      "received" : "2018-08-20T15:21:26.698741+00:00"
                 }
       '''

        when:
        Email entity = mapper.readValue(payload, Email);

        then:
        entity.emailType == EmailType.outgoing
        entity.messageId == '153477862836.8162.5467706423478211221@matt-ThinkPad-X1-Carbon-3rd'
        entity.id == 80
        entity.leadId == 238
        entity.sent == DateTime.parse("2018-08-20T15:23:48+00:00")
        entity.received == DateTime.parse("2018-08-20T15:21:26.698741+00:00")
        entity.to == 'Testing Matt <matt.wilson+test@twigdoo.com>'
        entity.from == 'Matt Wilson <matt.wilson@twigdoo.com>'
        entity.summaryBody == 'Hi friend'
        entity.subject == 'Hello!'
        entity.links.size() == 2
        entity.links['self'] == "http://localhost:5000/lead/238/emails/80"
        entity.links['lead'] == "http://localhost:5000/lead/238"
    }
}