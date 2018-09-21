package com.twigdoo

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.twigdoo.util.ObjectMapperFactory
import org.joda.time.DateTime
import org.joda.time.LocalDate
import spock.lang.Specification

class LeadSpec extends Specification {
    private ObjectMapper mapper = ObjectMapperFactory.make();

    def "I can covert a JSON payload to the entity"() {
        given:
        String payload = '''
                {
                    "source_id": "123",
                    "client": {
                        "name": "Tommy Tommyson",
                        "email": "tommy@tommysonstoms.com"
                    },
                    "service": {
                        "name": "Math Tutor",
                        "address": "SE1 0LH"
                    },
                    "data": {
                        "availability": "after 3pm"
                    }                
                }
       '''

        when:
        LeadRequest entity = mapper.readValue(payload, LeadRequest);

        then:
        with(entity) {
            sourceId == '123'
            client.name == 'Tommy Tommyson'
            client.email == 'tommy@tommysonstoms.com'
            service.name == 'Math Tutor'
            service.address == 'SE1 0LH'
            data.availability == 'after 3pm'
        }
    }

    def "I can covert an entity to JSON"() {
        given:
        DateTime now = DateTime.now();
        LocalDate today = LocalDate.now();
        LeadRequest lead = new LeadRequest()
                .withClient(new Client()
                    .withAddress("25 Blues Brother Drive")
                    .withEmail("jake@test.com")
                    .withName("Jake Blues")
                    .withPhone("555-123-123"))
                .withData(["item" : "item value"])
                .withDeletedOn(now)
                .withLostOn(now)
                .withLostReason("lost-reason")
                .withQualifiedOn(now)
                .withService(new Service()
                    .withName("sell")
                    .withAddress("address")
                    .withBudget(100)
                    .withCurrency("GBP")
                    .withDate(LocalDate.now()))
                .withSource("source-name")
                .withSourceId("source-id")
                .withStatus("active")

        when:
        String json = mapper.writeValueAsString(lead)
        Map<String, Object> values = mapper.readValue(json, new TypeReference<Map<String, Object>>() {})

        then:
        values['source_id'] == 'source-id'
        values['source'] == 'source-name'
        values['status'] == 'active'
        DateTime.parse(values['qualified_on']) == now
        values['lost_reason'] == 'lost-reason'
        DateTime.parse(values['lost_on']) == now
        DateTime.parse(values['deleted_on']) == now

        values['client']['name'] == 'Jake Blues'
        values['client']['email'] == 'jake@test.com'
        values['client']['phone'] == '555-123-123'
        values['client']['address'] == '25 Blues Brother Drive'

        values['service']['name'] == 'sell'
        values['service']['address'] == 'address'
        values['service']['budget'] == 100
        values['service']['currency'] == 'GBP'
        values['service']['date'] == today.toString()

        values['data']['item'] == 'item value'

        when:
        LeadRequest result = mapper.readValue(json, LeadRequest.class)

        then:
        result.sourceId == 'source-id'
        result.source == 'source-name'
        result.status == 'active'
        result.qualifiedOn == now
        result.lostReason == 'lost-reason'
        result.lostOn == now
        result.deletedOn == now

        result.client.name == 'Jake Blues'
        result.client.email == 'jake@test.com'
        result.client.phone == '555-123-123'
        result.client.address == '25 Blues Brother Drive'

        result.service.name == 'sell'
        result.service.address == 'address'
        result.service.budget == 100
        result.service.currency == 'GBP'
        result.service.date == today

        result.data['item'] == 'item value'
    }
}