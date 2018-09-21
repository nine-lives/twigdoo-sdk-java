package com.twigdoo

class LeadSmsIntegrationSpec extends BaseIntegrationSpec {

    def "I can get smses for a lead by id"() {
        given:
        Lead createResponse = twigdoo.create(buildLead());

        when:
        List<Sms> result = twigdoo.smses(createResponse.getId())

        then:
        result.size() == 0
    }

    def "I get a 404 when I fetch a smses for a lead with an invalid id"() {
        when:
        twigdoo.smses(-1)

        then:
        TwigdooServerException exception = thrown(TwigdooServerException)
        exception.error == null
        exception.statusCode == 404
        exception.statusMessage == 'NOT FOUND'
    }
}
