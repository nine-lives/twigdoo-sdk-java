package com.twigdoo

class LeadCreateIntegrationSpec extends BaseIntegrationSpec {

    def "I can create a lead"() {
        given:
        Lead lead = buildLead()

        when:
        LeadResponse result = twigdoo.create(lead);

        then:
        validate(lead, result)
    }

    def "I can create a lead with minimal data"() {
        given:
        Lead lead = new Lead()
            .withClient(new Client().withName("the-client").withEmail("client@test.com"))
            .withService(new Service().withName("the-service").withAddress("SE1 0LH"))

        when:
        LeadResponse result = twigdoo.create(lead);

        then:
        validate(lead, result)
    }

    def "I get a validation error for an invalid email"() {
        given:
        Lead lead = new Lead()
                .withClient(new Client().withName("the-client").withEmail("thats-no-moon"))
                .withService(new Service().withName("the-service").withAddress("SE1 0LH"))

        when:
        twigdoo.create(lead);

        then:
        TwigdooServerException exception = thrown(TwigdooServerException)
        exception.error != null
        exception.error.error == '{"error": {"invalid": [".client.email"]}}'
        exception.statusCode == 400
        exception.statusMessage == 'BAD REQUEST'
    }

    def "I get an error if I try to create a lead with no payload"() {
        when:
        twigdoo.create(new Lead());

        then:
        TwigdooServerException exception = thrown(TwigdooServerException)
        exception.error != null
        exception.error.error == 'Missing JSON data'
        exception.statusCode == 400
        exception.statusMessage == 'BAD REQUEST'
    }

    def "I get an error if I try to create a lead without the minimum required fields"() {
        when:
        twigdoo.create(new Lead().withSourceId(String.valueOf(System.currentTimeMillis())));

        then:
        TwigdooServerException exception = thrown(TwigdooServerException)
        exception.error != null
        exception.error.error == '{"error": {"missing": [".client", ".service"]}}'
        exception.statusCode == 400
        exception.statusMessage == 'BAD REQUEST'

    }

    def "I get an error if I try to create a structured lead without the minimum required fields"() {
        when:
        twigdoo.create(new Lead().withClient(new Client()).withService(new Service()));

        then:
        TwigdooServerException exception = thrown(TwigdooServerException)
        exception.error != null
        exception.error.error == '{"error": {"missing": [".client.email", ".client.name", ".service.address", ".service.name"]}}'
        exception.statusCode == 400
        exception.statusMessage == 'BAD REQUEST'
    }

    def "I get an error if I try to create a lead with an existing source id"() {
        given:
        Lead lead = buildLead();
        twigdoo.create(lead);

        when:
        twigdoo.create(lead);

        then:
        TwigdooServerException exception = thrown(TwigdooServerException)
        exception.error != null
        exception.error.error == 'source_id already exists'
        exception.statusCode == 409
        exception.statusMessage == 'CONFLICT'
    }
}
