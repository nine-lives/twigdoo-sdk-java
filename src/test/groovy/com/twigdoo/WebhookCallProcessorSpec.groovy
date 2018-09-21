package com.twigdoo

import com.fasterxml.jackson.core.JsonParseException
import spock.lang.Specification

import java.time.Clock
import java.util.concurrent.Future

class WebhookCallProcessorSpec extends Specification {
    def "I can process a lead created"() {
        given:
            WebhookCallProcessor processor = new WebhookCallProcessor(1)
            processor.addListener(new WebhookListener() {})
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
                            "service": { "name": "Testing & Winning? H", "address": "somewhere" }
                        }
                    }
           '''

        when:
        Lead result
        processor.addListener(new WebhookListener() {
            @Override
            void created(Webhook<Lead> hook, Lead lead) {
                result = lead
            }
        })
        processor.process(payload).get()

        then:
        result != null
        result.id == 1823
        result.client.name == 'Lead Testing'
    }

    def "I can process a lead updated"() {
        given:
        WebhookCallProcessor processor = new WebhookCallProcessor(1)
        processor.addListener(new WebhookListener() {})
        String payload = '''
                    {
                        "id": 858, 
                        "version": 1, 
                        "action": "updated", 
                        "entity": "lead", 
                        "timestamp": "2018-09-21T10:39:43.639594+00:00", 
                        "data": {
                            "id": 1823,
                            "client": { "name": "Lead Testing", "email": "codebrewery+fu2@gmail.com" }, 
                            "service": { "name": "Testing & Winning? H", "address": "somewhere" }
                        }
                    }
           '''


        when:
        Lead result
        processor.addListener(new WebhookListener() {
            @Override
            void updated(Webhook<Lead> hook, Lead lead) {
                result = lead
            }
        })
        processor.process(payload).get()

        then:
        result != null
        result.id == 1823
        result.client.name == 'Lead Testing'
    }

    def "I can process a call created"() {
        given:
        WebhookCallProcessor processor = new WebhookCallProcessor(1)
        processor.addListener(new WebhookListener() {})
        String payload = '''
                    {
                        "id": 858, 
                        "version": 1, 
                        "action": "created", 
                        "entity": "call", 
                        "timestamp": "2018-09-21T10:39:43.639594+00:00", 
                        "data": {
                           "id" : 1,
                           "call_type" : "outgoing"
                        }
                    }
           '''


        when:
        Call result
        processor.addListener(new WebhookListener() {
            @Override
            void created(Webhook<Call> hook, Call call) {
                result = call
            }
        })
        processor.process(payload).get()

        then:
        result != null
        result.id == 1
        result.callType == CallType.outgoing
    }

    def "I can process a call updated"() {
        given:
        WebhookCallProcessor processor = new WebhookCallProcessor(1)
        processor.addListener(new WebhookListener() {})
        String payload = '''
                    {
                        "id": 858, 
                        "version": 1, 
                        "action": "updated", 
                        "entity": "call", 
                        "timestamp": "2018-09-21T10:39:43.639594+00:00", 
                        "data": {
                           "id" : 1,
                           "call_type" : "outgoing_missed"
                        }
                    }
           '''


        when:
        Call result
        processor.addListener(new WebhookListener() {
            @Override
            void updated(Webhook<Call> hook, Call call) {
                result = call
            }
        })
        processor.process(payload).get()


        then:
        result != null
        result.id == 1
        result.callType == CallType.outgoing_missed
    }


    def "I can process an sms created"() {
        given:
        WebhookCallProcessor processor = new WebhookCallProcessor(1)
        processor.addListener(new WebhookListener() {})
        String payload = '''
                    {
                        "id": 858, 
                        "version": 1, 
                        "action": "created", 
                        "entity": "sms", 
                        "timestamp": "2018-09-21T10:39:43.639594+00:00", 
                        "data": {
                           "id" : 1,
                           "sms_type" : "outgoing"
                        }
                    }
           '''


        when:
        Sms result
        processor.addListener(new WebhookListener() {
            @Override
            void created(Webhook<Sms> hook, Sms sms) {
                result = sms
            }
        })
        processor.process(payload).get()

        then:
        result != null
        result.id == 1
        result.smsType == SmsType.outgoing
    }

    def "I can process a sms updated"() {
        given:
        WebhookCallProcessor processor = new WebhookCallProcessor(1)
        processor.addListener(new WebhookListener() {})
        String payload = '''
                    {
                        "id": 858, 
                        "version": 1, 
                        "action": "updated", 
                        "entity": "sms", 
                        "timestamp": "2018-09-21T10:39:43.639594+00:00", 
                        "data": {
                           "id" : 1,
                           "sms_type" : "outgoing"
                        }
                    }
           '''


        when:
        Sms result
        processor.addListener(new WebhookListener() {
            @Override
            void updated(Webhook<Sms> hook, Sms sms) {
                result = sms
            }
        })
        processor.process(payload).get()


        then:
        result != null
        result.id == 1
        result.smsType == SmsType.outgoing
    }

    def "I can handle parsing errors"() {
        given:
        WebhookCallProcessor processor = new WebhookCallProcessor(1)
        processor.addListener(new WebhookListener() {})
        String payload = '{invalid"json}'


        when:
        Exception resultException
        String resultPayload
        processor.addListener(new WebhookListener() {
            @Override
            void error(Exception e, String received) {
                resultException = e
                resultPayload = received
            }
        })
        processor.process(payload).get()


        then:
        resultException != null
        resultException.class == JsonParseException
        resultPayload != null
        resultPayload == payload
    }

    def "I can handle listener errors"() {
        given:
        WebhookCallProcessor processor = new WebhookCallProcessor(1)
        processor.addListener(new WebhookListener() {})
        String payload = '''
                    {
                        "id": 858, 
                        "version": 1, 
                        "action": "updated", 
                        "entity": "sms", 
                        "timestamp": "2018-09-21T10:39:43.639594+00:00", 
                        "data": {
                           "id" : 1,
                           "sms_type" : "outgoing"
                        }
                    }
           '''

        when:
        Exception resultException
        String resultPayload
        processor.addListener(new WebhookListener() {
            @Override
            void updated(Webhook<Sms> hook, Sms sms) {
                throw new IllegalStateException()
            }
            @Override
            void error(Exception e, String received) {
                resultException = e
                resultPayload = received
            }
        })
        processor.process(payload).get()


        then:
        resultException != null
        resultException.class == IllegalStateException
        resultPayload != null
        resultPayload == payload
    }

    def "I can handle error listener errors"() {
        given:
        WebhookCallProcessor processor = new WebhookCallProcessor(1)
        processor.addListener(new WebhookListener() {})
        String payload = '''
                    {
                        "id": 858, 
                        "version": 1, 
                        "action": "updated", 
                        "entity": "sms", 
                        "timestamp": "2018-09-21T10:39:43.639594+00:00", 
                        "data": {
                           "id" : 1,
                           "sms_type" : "outgoing"
                        }
                    }
           '''

        when:
        processor.addListener(new WebhookListener() {
            @Override
            void error(Exception e, String received) {
                throw new IllegalStateException()
            }
        })
        Object o = processor.process(payload).get()


        then:
        o == null
    }

    def "The default number of threads is 5"() {
        given:
        WebhookCallProcessor processor = new WebhookCallProcessor()
        processor.addListener(new WebhookListener() {})
        String payload = '''
                    {
                        "id": 858, 
                        "version": 1, 
                        "action": "updated", 
                        "entity": "sms", 
                        "timestamp": "2018-09-21T10:39:43.639594+00:00", 
                        "data": {
                           "id" : 1,
                           "sms_type" : "outgoing"
                        }
                    }
           '''

        when:
        processor.addListener(new WebhookListener() {
            @Override
            void updated(Webhook<Sms> hook, Sms sms) {
                Thread.sleep(1000)
            }
            @Override
            void error(Exception e, String received) {
                e.printStackTrace()
            }
        })

        long startTime = -Clock.systemUTC().millis()
        List<Future> futures = (0..4).collect {
            processor.process(payload)
        }
        Future future = processor.process(payload)

        then:
        (startTime + Clock.systemUTC().millis()) < 1000

        when:
        futures.each { it.get() }

        then:
        (startTime + Clock.systemUTC().millis()) >= 1000
        (startTime + Clock.systemUTC().millis()) < 5000

        when:
        future.get()

        then:
        (startTime + Clock.systemUTC().millis()) >= 2000
    }

    def "I can add and remove multiple listeners"() {
        given:
        WebhookCallProcessor processor = new WebhookCallProcessor()
        processor.addListener(new WebhookListener() {})
        String payload = '''
                    {
                        "id": 858, 
                        "version": 1, 
                        "action": "updated", 
                        "entity": "sms", 
                        "timestamp": "2018-09-21T10:39:43.639594+00:00", 
                        "data": {
                           "id" : 1,
                           "sms_type" : "outgoing"
                        }
                    }
           '''

        when:
        Sms sms1
        Sms sms2
        WebhookListener l1 = new WebhookListener() {
            @Override
            void updated(Webhook<Sms> hook, Sms sms) {
                sms1 = sms
            }
        }

        WebhookListener l2 = new WebhookListener() {
            @Override
            void updated(Webhook<Sms> hook, Sms sms) {
                sms2 = sms
            }
        }

        processor.addListener(l1)
        processor.addListener(l2)
        processor.process(payload).get()

        then:
        sms1 != null
        sms2 != null
        sms1.smsType == SmsType.outgoing
        sms2.smsType == SmsType.outgoing

        when:
        sms1 = null
        sms2 = null

        processor.removeListener(l2)
        processor.process(payload).get()

        then:
        sms1 != null
        sms2 == null
        sms1.smsType == SmsType.outgoing
    }
}
