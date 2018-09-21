package com.twigdoo

class LeadUpdateSpec extends BaseIntegrationSpec {

    def "I can update a lead by id"() {
        Lead createResponse = twigdoo.create(buildLead());
        LeadRequest updatedLead = buildAltLead().withSourceId(createResponse.getSourceId())

        when:
        Lead result = twigdoo.update(createResponse.getId(), updatedLead)

        then:
        validate(updatedLead, result)
    }

    def "I can update a lead by source id"() {
        Lead createResponse = twigdoo.create(buildLead());
        LeadRequest updatedLead = buildAltLead().withSourceId(createResponse.getSourceId())

        when:
        Lead result = twigdoo.update(updatedLead)

        then:
        validate(updatedLead, result)
    }

    def "I can update a lead and change the source id"() {
        given:
        Lead createResponse = twigdoo.create(buildLead());
        LeadRequest updatedLead = buildAltLead()

        when:
        Lead result = twigdoo.update(createResponse.getId(), updatedLead)

        then:
        validate(updatedLead, result)
    }

    def "I can update a client fullname with just a forename successfully"() {
        given:
        LeadRequest lead = buildLead()
        Lead createResponse = twigdoo.create(lead);
        lead.getClient().withName("Elwood")

        when:
        Lead result = twigdoo.update(createResponse.getId(), lead)

        then:
        validate(lead, result)
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

    def "I can update and clear the source id successfully"() {
        given:
        LeadRequest lead = buildLead()
        Lead createResponse = twigdoo.create(lead);
        lead.withSourceId(null)

        when:
        Lead result = twigdoo.update(createResponse.getId(), lead)

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
        LeadRequest lead = buildLead()
        Lead createResponse = twigdoo.create(lead);
        lead.withSource(null)

        when:
        Lead result = twigdoo.update(createResponse.getId(), lead)

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
        LeadRequest lead = buildLead()
        Lead createResponse = twigdoo.create(lead);
        lead.getClient().withPhone("")

        when:
        Lead result = twigdoo.update(createResponse.getId(), lead)

        then:
        validate(lead, result)

        when:
        lead.getClient().withPhone(null)
        result = twigdoo.update(createResponse.getId(), lead)

        then:
        validate(lead, result)
    }

    def "I can update and clear the client address successfully"() {
        given:
        LeadRequest lead = buildLead()
        Lead createResponse = twigdoo.create(lead);
        lead.getClient().withAddress("")

        when:
        Lead result = twigdoo.update(createResponse.getId(), lead)

        then:
        validate(lead, result)

        when:
        lead.getClient().withAddress(null)
        result = twigdoo.update(createResponse.getId(), lead)

        then:
        validate(lead, result)
    }

    def "I can update and clear the budget successfully"() {
        given:
        LeadRequest lead = buildLead()
        Lead createResponse = twigdoo.create(lead);
        lead.getService().withBudget(null)

        when:
        Lead result = twigdoo.update(createResponse.getId(), lead)

        then:
        validate(lead, result)
    }

    def "I can update and clear the currency successfully"() {
        given:
        LeadRequest lead = buildLead()
        Lead createResponse = twigdoo.create(lead);
        lead.getService().withCurrency(null)

        when:
        Lead result = twigdoo.update(createResponse.getId(), lead)

        then:
        validate(lead, result)
    }

    def "I can update and clear the data successfully"() {
        given:
        LeadRequest lead = buildLead()
        Lead createResponse = twigdoo.create(lead);
        lead.withData([:])

        when:
        Lead result = twigdoo.update(createResponse.getId(), lead)

        then:
        validate(lead, result)

        when:
        lead.withData(null)
        result = twigdoo.update(createResponse.getId(), lead)

        then:
        validate(lead, result)
    }

    def "I can update a lead with minimal data"() {
        given:
        Lead createResponse = twigdoo.create(buildLead());
        LeadRequest updatedLead = new LeadRequest()
                .withClient(new Client().withName("the client").withEmail("client@test.com"))
                .withService(new Service().withName("the-service").withAddress("SE1 0LH"))

        // Fields that don't currently get cleared
        updatedLead
                .withSourceId(createResponse.getSourceId())
                .withSource(createResponse.getSource())
                .withData(createResponse.getData())
        updatedLead.getClient()
                .withPhone(createResponse.getClient().getPhone())
                .withAddress(createResponse.getClient().getAddress())
        updatedLead.getService()
                .withBudget(createResponse.getService().getBudget())
                .withCurrency(createResponse.getService().getCurrency())

        when:
        Lead result = twigdoo.update(createResponse.getId(), updatedLead)

        then:
        validate(updatedLead, result)
    }

    def "I get a validation error for an invalid email"() {
        given:
        LeadRequest lead = buildLead()
        Lead createResponse = twigdoo.create(lead);
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
        Lead createResponse = twigdoo.create(buildLead());

        when:
        twigdoo.update(createResponse.getId(), new LeadRequest())

        then:
        TwigdooServerException exception = thrown(TwigdooServerException)
        exception.error != null
        exception.error.error == 'Missing JSON data'
        exception.statusCode == 400
        exception.statusMessage == 'BAD REQUEST'
    }

    def "I get an error if I try to update a lead without the minimum required fields"() {
        given:
        Lead createResponse = twigdoo.create(buildLead());

        when:
        twigdoo.update(createResponse.getId(), new LeadRequest().withSourceId(String.valueOf(System.currentTimeMillis())))

        then:
        TwigdooServerException exception = thrown(TwigdooServerException)
        exception.error != null
        exception.error.error == '{"error": {"missing": [".client", ".service"]}}'
        exception.statusCode == 400
        exception.statusMessage == 'BAD REQUEST'

    }

    def "I get an error if I try to create a structured lead without the minimum required fields"() {
        given:
        Lead createResponse = twigdoo.create(buildLead());

        when:
        twigdoo.update(createResponse.getId(), new LeadRequest().withClient(new Client()).withService(new Service()))

        then:
        TwigdooServerException exception = thrown(TwigdooServerException)
        exception.error != null
        exception.error.error == '{"error": {"missing": [".client.email", ".client.name", ".service.address", ".service.name"]}}'
        exception.statusCode == 400
        exception.statusMessage == 'BAD REQUEST'
    }
}
