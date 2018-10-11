package com.twigdoo

class LeadEmailIntegrationSpec extends BaseIntegrationSpec {

    def "I can get emails for a lead by id"() {
        given:
        Lead createResponse = twigdoo.create(buildLead());

        when:
        List<Email> result = twigdoo.emails(createResponse.getId())

        then:
        result.size() == 0
    }

    def "I get a 404 when I fetch a emails for a lead with an invalid id"() {
        when:
        twigdoo.emails(-1)

        then:
        TwigdooServerException exception = thrown(TwigdooServerException)
        exception.error == null
        exception.statusCode == 404
        exception.statusMessage == 'NOT FOUND'
    }
}
