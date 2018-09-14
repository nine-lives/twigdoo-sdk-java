package com.twigdoo

class LeadPatchSpec extends BaseIntegrationSpec {

    def "I can patch a lead by id"() {
        LeadResponse createResponse = twigdoo.create(buildLead());
        Lead updatedLead = buildAltLead().withSourceId(createResponse.getSourceId())

        when:
        LeadResponse result = twigdoo.patch(createResponse.getId(), updatedLead)

        then:
        validate(updatedLead, result)
    }

    def "I can patch a lead by source id"() {
        LeadResponse createResponse = twigdoo.create(buildLead());
        Lead updatedLead = buildAltLead().withSourceId(createResponse.getSourceId())

        when:
        LeadResponse result = twigdoo.patch(updatedLead)

        then:
        validate(updatedLead, result)
    }

    def "I can patch a lead and change the source id"() {
        given:
        LeadResponse createResponse = twigdoo.create(buildLead());
        Lead updatedLead = buildAltLead()

        when:
        LeadResponse result = twigdoo.patch(createResponse.getId(), updatedLead)

        then:
        validate(updatedLead, result)
    }

    def "I can patch a client fullname with just a forename successfully"() {
        given:
        Lead lead = buildLead();
        LeadResponse createResponse = twigdoo.create(lead);

        when:
        LeadResponse result = twigdoo.patch(createResponse.getId(), new Lead().withClient(new Client().withName("Elwood")))

        then:
        result.client.name == 'Elwood'
    }


    def "I get a validation error for an invalid email"() {
        given:
        LeadResponse createResponse = twigdoo.create(buildLead());

        when:
        twigdoo.patch(createResponse.getId(), new Lead().withClient(new Client().withEmail("i-am-your-father")))

        then:
        TwigdooServerException exception = thrown(TwigdooServerException)
        exception.error != null
        exception.error.error == '{"error": {"invalid": [".client.email"]}}'
        exception.statusCode == 400
        exception.statusMessage == 'BAD REQUEST'
    }

    def "I can update data successfully"() {
        given:
        Lead lead = buildLead()
        LeadResponse createResponse = twigdoo.create(lead);
        lead.withData(['Item 1': 'Mic'])

        when:
        LeadResponse result = twigdoo.update(createResponse.getId(), lead)

        then:
        validate(lead, result)
    }
}
