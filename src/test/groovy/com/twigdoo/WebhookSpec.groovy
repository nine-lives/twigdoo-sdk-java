package com.twigdoo

import com.fasterxml.jackson.databind.ObjectMapper
import com.twigdoo.util.ObjectMapperFactory
import org.joda.time.DateTime
import spock.lang.Specification

class WebhookSpec extends Specification {
    private ObjectMapper mapper = ObjectMapperFactory.make()

    def "I can covert a JSON payload to the lead webhook"() {
        given:
        String payload = '''
                {
                    "id": 858, 
                    "version": 1, 
                    "action": "created", 
                    "entity": "lead", 
                    "timestamp": "2018-09-21T10:39:43.639594+00:00", 
                    "data": {
                        "id": 1823,
                        "client": { "name": "Lead Testing", "email": "codebrewery+fu2@gmail.com" }, 
                        "service": { "name": "Testing & Winning? H", "address": "somewhere" },
                        "created_on": "2018-09-21T10:39:43.519521+00:00", 
                        "updated_on": "2018-09-21T10:39:43.577470+00:00" 
                    }
                }
       '''

        when:
        TwigdooEntityType type = Webhook.findEntityType(mapper, payload);

        then:
        type ==  TwigdooEntityType.lead

        when:
        Webhook<Lead> entity = Webhook.getLead(mapper, payload);

        then:
        entity.id == 858
        entity.version == 1
        entity.action == WebhookAction.created
        entity.entity == TwigdooEntityType.lead
        entity.timestamp == DateTime.parse("2018-09-21T10:39:43.639594+00:00")
        entity.data.id == 1823
        entity.data.client.name == 'Lead Testing'
    }


    def "I can covert a JSON payload to the call webhook"() {
        given:
        String payload = '''
                {
                    "id": 858, 
                    "version": 1, 
                    "action": "created", 
                    "entity": "call", 
                    "timestamp": "2018-09-21T10:39:43.639594+00:00", 
                    "data": {
                       "call_type" : "incoming",
                       "contact_number" : "07411888888",
                       "id" : 1,
                       "lead_id" : 301,
                       "duration" : 123,
                       "links" : {
                          "self" : "http://localhost:5000/lead/301/calls/1",
                          "lead" : "http://localhost:5000/lead/301"
                       },
                       "call_time" : "2018-09-19T15:35:53.549593+00:00",
                       "notes" : "notes here"
                    }
                }
       '''

        when:
        TwigdooEntityType type = Webhook.findEntityType(mapper, payload);

        then:
        type ==  TwigdooEntityType.call

        when:
        Webhook<Call> entity = Webhook.getCall(mapper, payload);

        then:
        entity.id == 858
        entity.version == 1
        entity.action == WebhookAction.created
        entity.entity == TwigdooEntityType.call
        entity.timestamp == DateTime.parse("2018-09-21T10:39:43.639594+00:00")
        entity.data.id == 1
        entity.data.duration == 123
    }

    def "I can covert a JSON payload to the sms webhook"() {
        given:
        String payload = '''
                {
                    "id": 858, 
                    "version": 1, 
                    "action": "created", 
                    "entity": "sms", 
                    "timestamp": "2018-09-21T10:39:43.639594+00:00", 
                    "data": {
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
                }
       '''

        when:
        TwigdooEntityType type = Webhook.findEntityType(mapper, payload);

        then:
        type ==  TwigdooEntityType.sms

        when:
        Webhook<Sms> entity = Webhook.getSms(mapper, payload);

        then:
        entity.id == 858
        entity.version == 1
        entity.action == WebhookAction.created
        entity.entity == TwigdooEntityType.sms
        entity.timestamp == DateTime.parse("2018-09-21T10:39:43.639594+00:00")
        entity.data.id == 1
        entity.data.smsType == SmsType.incoming
    }
}
