package com.twigdoo

import org.joda.time.LocalDate

class LeadGetIntegrationSpec extends BaseIntegrationSpec {

    def "I can get a lead by id"() {
        given:
        LeadRequest lead = buildLead()
        Lead createResponse = twigdoo.create(lead);

        when:
        Lead result = twigdoo.get(createResponse.getId())

        then:
        validate(lead, result)
    }

    def "I can get a lead by source id"() {
        given:
        LeadRequest lead = buildLead()
        Lead createResponse = twigdoo.create(lead);

        when:
        Lead result = twigdoo.get(lead.getSourceId())

        then:
        validate(lead, result)
    }


    def "Service date is returned when set"() {
        given:
        LeadRequest lead = buildLead()
        lead.getService().withDate(LocalDate.now())
        Lead createResponse = twigdoo.create(lead);

        when:
        Lead result = twigdoo.get(createResponse.getId())

        then:
        validate(lead, result)
    }

    def "I get a 404 when I fetch a lead with an invalid id"() {
        when:
        twigdoo.get(-1)

        then:
        TwigdooServerException exception = thrown(TwigdooServerException)
        exception.error == null
        exception.statusCode == 404
        exception.statusMessage == 'NOT FOUND'
    }

    def "I can get a 404 when I fetch a lead with an invalid source id"() {
        when:
        twigdoo.get("better-not-exist")

        then:
        TwigdooServerException exception = thrown(TwigdooServerException)
        exception.error == null
        exception.statusCode == 404
        exception.statusMessage == 'NOT FOUND'
    }
}
