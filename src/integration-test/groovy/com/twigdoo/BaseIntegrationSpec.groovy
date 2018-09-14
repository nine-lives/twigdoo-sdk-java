package com.twigdoo

import com.twigdoo.util.ObjectMapperFactory
import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.joda.time.Minutes
import spock.lang.Specification

abstract class BaseIntegrationSpec extends Specification {
    private static final LocalDate TODAY = LocalDate.now();

    protected static Twigdoo twigdoo

    def setupSpec() {
        ObjectMapperFactory.setFailOnUnknownProperties(true)
        twigdoo = Twigdoo.make(new Configuration()
                .withEndpoint(System.getProperty("twigdooEndpoint") ?: System.getenv("twigdooEndpoint") ?: "https://api-dev.twigdoo.com")
                .withApiKey(System.getProperty("twigdooApiKey") ?: System.getenv("twigdooApiKey")))
    }

    def validate(Lead lead, LeadResponse result, String status = 'active') {
        assert result.id ==~ /\d+/
        assert Math.abs(Minutes.minutesBetween(result.createdOn, DateTime.now()).minutes) < 5
        assert Math.abs(Minutes.minutesBetween(result.updatedOn, DateTime.now()).minutes) < 5
        assert result.stage == ""
        assert result.links.size() == 2
        assert result.links["self"] ==~ /https:\/\/api-dev.twigdoo.com\/lead\/\d+/ || result.links["self"] ==~ /https:\/\/api.twigdoo.com\/lead\/\d+/
        assert result.links["calls"] ==~ /https:\/\/api-dev.twigdoo.com\/lead\/\d+\/calls/ || result.links["calls"] ==~ /https:\/\/api.twigdoo.com\/lead\/\d+\/calls/

        assert result.sourceId == lead.getSourceId()
        assert result.source == lead.source
        assert result.status == status

        assert result.client.name == lead.client.name
        assert result.client.email == lead.client.email
        assert result.client.phone == (lead.client.phone ?: '')
        assert result.client.address == (lead.client.address ?: '')

        assert result.service.name == lead.service.name
        assert result.service.address == lead.service.address
        assert result.service.budget == lead.service.budget
        assert result.service.currency == lead.service.currency
        assert result.service.date == lead.service.date

        assert result.data == (lead.data ?: [:])
        true
    }

    Lead buildLead() {
        new Lead()
                .withClient(new Client()
                .withAddress("26 Blues Brother Drive")
                .withEmail("jake@test.com")
                .withName("Jake Blues")
                .withPhone("555-123-123"))
                .withData(["Item 1": "Harp", "Item 2": "Keyboard with shot keys"])
                .withService(new Service()
                .withName("Ray's Music Exchange")
                .withAddress("Chicago")
                //.withDate(TODAY)
                .withBudget(100)
                .withCurrency("GBP"))
                .withSource("blues-brother.com")
                .withSourceId(String.valueOf(System.currentTimeMillis()))
    }

    Lead buildAltLead() {
        new Lead()
                .withClient(new Client()
                .withAddress("62 Blues Brother Drive")
                .withEmail("elwood@test.com")
                .withName("Elwood Blues")
                .withPhone("555-321-321"))
                .withData(["Item 1": "Harp", "Item 2": "Keyboard with shot keys"])
                .withService(new Service()
                .withName("Nate's Deli")
                .withAddress("Atlanta")
                //.withDate(TODAY)
                .withBudget(101)
                .withCurrency("GBP"))
                .withSource("blues-brother2.com")
                .withSourceId(String.valueOf(System.currentTimeMillis()))
    }
}
