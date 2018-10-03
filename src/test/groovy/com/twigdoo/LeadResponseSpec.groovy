package com.twigdoo

import com.fasterxml.jackson.databind.ObjectMapper
import com.twigdoo.util.ObjectMapperFactory
import org.joda.time.DateTimeZone
import org.joda.time.LocalDateTime
import spock.lang.Specification

class LeadResponseSpec extends Specification {
    private ObjectMapper mapper = ObjectMapperFactory.make();

    def "I can covert a JSON payload to the entity"() {
        given:
        String payload = '''
            {
              "source_id" : "12345",
              "source" : "blues-brother.com",
              "client" : {
                "name" : "Jake Blues",
                "phone" : "555-123-123",
                "email" : "jake@test.com",
                "address" : "30 Blues Brother Drive"
              },
              "service" : {
                "name" : "Ray's Music Exchange",
                "address" : "Chicago, IL, USA",
                "budget" : 100,
                "currency" : "GBP"
              },
              "status" : "qualified",
              "qualified_on" : "2018-09-14T14:37:10.364",
              "data" : {
                "Item 2" : "Keyboard with shot keys"
              },
              "id" : 491,
              "created_on" : "2018-09-13T13:22:03.742",
              "updated_on" : "2018-09-14T12:41:48.058",
              "stage" : "New Deal",
              "links" : {
                "self" : "https://api-dev.twigdoo.com/lead/491",
                "calls" : "https://api-dev.twigdoo.com/lead/491/calls",
                "smses" : "https://api-dev.twigdoo.com/lead/491/smses",
                "emails" : "https://api-dev.twigdoo.com/lead/491/emails"
              }
            }
       '''

        when:
        Lead result = mapper.readValue(payload, Lead);

        then:

        result.id == 491
        result.createdOn == LocalDateTime.parse('2018-09-13T13:22:03.742').toDateTime(DateTimeZone.UTC)
        result.updatedOn == LocalDateTime.parse('2018-09-14T12:41:48.058').toDateTime(DateTimeZone.UTC)
        result.stage == "New Deal"
        result.links.size() == 4
        result.links["self"] == 'https://api-dev.twigdoo.com/lead/491'
        result.links["calls"] == 'https://api-dev.twigdoo.com/lead/491/calls'
        result.links["smses"] == 'https://api-dev.twigdoo.com/lead/491/smses'
        result.links["emails"] == 'https://api-dev.twigdoo.com/lead/491/emails'

        result.sourceId == '12345'
        result.source == 'blues-brother.com'
        result.status == 'qualified'
        result.qualifiedOn == LocalDateTime.parse('2018-09-14T14:37:10.364').toDateTime(DateTimeZone.UTC)

        result.client.name == 'Jake Blues'
        result.client.email == 'jake@test.com'
        result.client.phone == '555-123-123'
        result.client.address == '30 Blues Brother Drive'

        result.service.name == 'Ray\'s Music Exchange'
        result.service.address == 'Chicago, IL, USA'
        result.service.budget == 100
        result.service.currency == 'GBP'
    }

}
