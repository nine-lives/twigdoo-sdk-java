package com.twigdoo

class LeadPatchSpec extends BaseIntegrationSpec {

    def "I can patch a lead by id"() {
        Lead createResponse = twigdoo.create(buildLead());
        LeadRequest updatedLead = buildAltLead().withSourceId(createResponse.getSourceId())

        when:
        Lead result = twigdoo.patch(createResponse.getId(), updatedLead)

        then:
        validate(updatedLead, result)
    }

    def "I can patch a lead by source id"() {
        Lead createResponse = twigdoo.create(buildLead());
        LeadRequest updatedLead = buildAltLead().withSourceId(createResponse.getSourceId())

        when:
        Lead result = twigdoo.patch(updatedLead)

        then:
        validate(updatedLead, result)
    }

    def "I can patch a lead and change the source id"() {
        given:
        Lead createResponse = twigdoo.create(buildLead());
        LeadRequest updatedLead = buildAltLead()

        when:
        Lead result = twigdoo.patch(createResponse.getId(), updatedLead)

        then:
        validate(updatedLead, result)
    }

    def "I can patch a client fullname with just a forename successfully"() {
        given:
        LeadRequest lead = buildLead();
        Lead createResponse = twigdoo.create(lead);

        when:
        Lead result = twigdoo.patch(createResponse.getId(), new LeadRequest().withClient(new Client().withName("Elwood")))

        then:
        result.client.name == 'Elwood'
    }


    def "I get a validation error for an invalid email"() {
        given:
        Lead createResponse = twigdoo.create(buildLead());

        when:
        twigdoo.patch(createResponse.getId(), new LeadRequest().withClient(new Client().withEmail("i-am-your-father")))

        then:
        TwigdooServerException exception = thrown(TwigdooServerException)
        exception.error != null
        exception.error.message == 'Invalid request body'
        exception.error.messageCode == 400
        exception.error.errors.invalid as Set == ['.client.email'] as Set
        exception.statusCode == 400
        exception.statusMessage == 'BAD REQUEST'
    }

    def "I can update data successfully"() {
        given:
        LeadRequest lead = buildLead()
        Lead createResponse = twigdoo.create(lead);
        lead.withData(['Item 1': 'Mic'])

        when:
        Lead result = twigdoo.update(createResponse.getId(), lead)

        then:
        validate(lead, result)
    }
}
