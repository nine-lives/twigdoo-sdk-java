package com.twigdoo

class LeadUpdateSpec extends BaseIntegrationSpec {

    def "I can update a lead by id"() {
        LeadResponse createResponse = twigdoo.create(buildLead());
        Lead updatedLead = buildAltLead().withSourceId(createResponse.getSourceId())

        when:
        LeadResponse result = twigdoo.update(createResponse.getId(), updatedLead)

        then:
        validate(updatedLead, result)
    }

    def "I can update a lead by source id"() {
        LeadResponse createResponse = twigdoo.create(buildLead());
        Lead updatedLead = buildAltLead().withSourceId(createResponse.getSourceId())

        when:
        LeadResponse result = twigdoo.update(updatedLead)

        then:
        validate(updatedLead, result)
    }

    def "I can update a lead and change the source id"() {
        given:
        LeadResponse createResponse = twigdoo.create(buildLead());
        Lead updatedLead = buildAltLead()

        when:
        LeadResponse result = twigdoo.update(createResponse.getId(), updatedLead)

        then:
        validate(updatedLead, result)
    }

    def "I can update a client fullname with just a forename successfully"() {
        given:
        Lead lead = buildLead()
        LeadResponse createResponse = twigdoo.create(lead);
        lead.getClient().withName("Elwood")

        when:
        LeadResponse result = twigdoo.update(createResponse.getId(), lead)

        then:
        validate(lead, result)
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

    def "I can update and clear the source id successfully"() {
        given:
        Lead lead = buildLead()
        LeadResponse createResponse = twigdoo.create(lead);
        lead.withSourceId(null)

        when:
        LeadResponse result = twigdoo.update(createResponse.getId(), lead)

        then:
        validate(lead, result)

        when:
        lead.withSourceId("")
        result = twigdoo.update(createResponse.getId(), lead)

        then:
        validate(lead, result)
    }

    def "I can update and clear the source successfully"() {
        given:
        Lead lead = buildLead()
        LeadResponse createResponse = twigdoo.create(lead);
        lead.withSource(null)

        when:
        LeadResponse result = twigdoo.update(createResponse.getId(), lead)

        then:
        validate(lead, result)

        when:
        lead.withSource("")
        result = twigdoo.update(createResponse.getId(), lead)

        then:
        validate(lead, result)
    }

    def "I can update and clear the client phone successfully"() {
        given:
        Lead lead = buildLead()
        LeadResponse createResponse = twigdoo.create(lead);
        lead.getClient().withPhone("")

        when:
        LeadResponse result = twigdoo.update(createResponse.getId(), lead)

        then:
        validate(lead, result)

        when:
        lead.getClient().withPhone(null)
        result = twigdoo.update(createResponse.getId(), lead)

        then:
        validate(lead, result)
    }

    def "I can update a lead with minimal data"() {
        given:
        LeadResponse createResponse = twigdoo.create(buildLead());
        Lead updatedLead = new Lead()
                .withClient(new Client().withName("the client").withEmail("client@test.com"))
                .withService(new Service().withName("the-service").withAddress("SE1 0LH"))
                .withSourceId(createResponse.getSourceId())
                .withSource(createResponse.getSource())

        when:
        LeadResponse result = twigdoo.update(createResponse.getId(), updatedLead)

        then:
        validate(updatedLead, result)
    }

    def "I get a validation error for an invalid email"() {
        given:
        Lead lead = buildLead()
        LeadResponse createResponse = twigdoo.create(lead);
        lead.getClient().withEmail("laugh-it-up-fuzzball")

        when:
        twigdoo.update(createResponse.getId(), lead)

        then:
        TwigdooServerException exception = thrown(TwigdooServerException)
        exception.error != null
        exception.error.error == '{"error": {"invalid": [".client.email"]}}'
        exception.statusCode == 400
        exception.statusMessage == 'BAD REQUEST'
    }

    def "I get an error if I try to update a lead with no payload"() {
        given:
        LeadResponse createResponse = twigdoo.create(buildLead());

        when:
        twigdoo.update(createResponse.getId(), new Lead())

        then:
        TwigdooServerException exception = thrown(TwigdooServerException)
        exception.error != null
        exception.error.error == 'Missing JSON data'
        exception.statusCode == 400
        exception.statusMessage == 'BAD REQUEST'
    }

    def "I get an error if I try to update a lead without the minimum required fields"() {
        given:
        LeadResponse createResponse = twigdoo.create(buildLead());

        when:
        twigdoo.update(createResponse.getId(), new Lead().withSourceId(String.valueOf(System.currentTimeMillis())))

        then:
        TwigdooServerException exception = thrown(TwigdooServerException)
        exception.error != null
        exception.error.error == '{"error": {"missing": [".client", ".service"]}}'
        exception.statusCode == 400
        exception.statusMessage == 'BAD REQUEST'

    }

    def "I get an error if I try to create a structured lead without the minimum required fields"() {
        given:
        LeadResponse createResponse = twigdoo.create(buildLead());

        when:
        twigdoo.update(createResponse.getId(), new Lead().withClient(new Client()).withService(new Service()))

        then:
        TwigdooServerException exception = thrown(TwigdooServerException)
        exception.error != null
        exception.error.error == '{"error": {"missing": [".client.email", ".client.name", ".service.address", ".service.name"]}}'
        exception.statusCode == 400
        exception.statusMessage == 'BAD REQUEST'
    }
}
