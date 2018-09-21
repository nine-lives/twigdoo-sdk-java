package com.twigdoo

class LeadCallsIntegrationSpec extends BaseIntegrationSpec {

    def "I can get calls for a lead by id"() {
        given:
        Lead createResponse = twigdoo.create(buildLead());

        when:
        List<Call> result = twigdoo.calls(createResponse.getId())

        then:
        result.size() == 0
    }

    def "I get a 404 when I fetch a calls for a lead with an invalid id"() {
        when:
        twigdoo.calls(-1)

        then:
        TwigdooServerException exception = thrown(TwigdooServerException)
        exception.error == null
        exception.statusCode == 404
        exception.statusMessage == 'NOT FOUND'
    }
}
