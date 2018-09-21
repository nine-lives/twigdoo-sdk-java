package com.twigdoo

import org.joda.time.DateTime
import org.joda.time.Minutes

class LeadDeleteIntegrationSpec extends BaseIntegrationSpec {

    def "I can delete a lead by id"() {
        given:
        LeadRequest lead = buildLead()
        Lead createResponse = twigdoo.create(lead);

        when:
        twigdoo.delete(createResponse.getId())
        LeadRequest result = twigdoo.get(createResponse.getId())

        then:
        result.status == 'deleted'
        assert Math.abs(Minutes.minutesBetween(result.deletedOn, DateTime.now()).minutes) < 5
    }

    def "I can delete a lead by source id"() {
        given:
        LeadRequest lead = buildLead()
        Lead createResponse = twigdoo.create(lead);

        when:
        twigdoo.delete(createResponse.getSourceId())
        LeadRequest result = twigdoo.get(createResponse.getId())

        then:
        result.status == 'deleted'
        assert Math.abs(Minutes.minutesBetween(result.deletedOn, DateTime.now()).minutes) < 5
    }


    def "I get a 404 when I delete a lead with an invalid id"() {
        when:
        twigdoo.delete(Integer.MAX_VALUE)

        then:
        TwigdooServerException exception = thrown(TwigdooServerException)
        exception.error == null
        exception.statusCode == 404
        exception.statusMessage == 'NOT FOUND'
    }

    def "I get a 404 when I delete a lead with an invalid source id"() {
        when:
        twigdoo.get("better-not-exist")

        then:
        TwigdooServerException exception = thrown(TwigdooServerException)
        exception.error == null
        exception.statusCode == 404
        exception.statusMessage == 'NOT FOUND'
    }
}
