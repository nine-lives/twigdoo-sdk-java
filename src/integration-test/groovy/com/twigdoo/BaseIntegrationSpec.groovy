package com.twigdoo

import com.twigdoo.util.ObjectMapperFactory
import org.joda.time.DateTime
import org.joda.time.Minutes
import spock.lang.Specification

abstract class BaseIntegrationSpec extends Specification {

    protected static Twigdoo twigdoo

    def setupSpec() {
        ObjectMapperFactory.setFailOnUnknownProperties(true)
        twigdoo = Twigdoo.make(new Configuration()
                .withBlockTillRateLimitReset(true)
                .withRequestsPerSecond(1000)
                .withEndpoint(System.getProperty("twigdooEndpoint") ?: System.getenv("twigdooEndpoint") ?: "https://api-dev.twigdoo.com")
                .withApiKey(System.getProperty("twigdooApiKey") ?: System.getenv("twigdooApiKey")))

    }

    def validate(LeadRequest lead, Lead result, String status = 'active') {
        assert result.id ==~ /\d+/
        assert Math.abs(Minutes.minutesBetween(result.createdOn, DateTime.now()).minutes) < 5
        assert Math.abs(Minutes.minutesBetween(result.updatedOn, DateTime.now()).minutes) < 5
        assert result.stage == "" || result.stage == "New Deal"
        assert result.links.size() == 4
        assert result.links["self"] ==~ /http[s]?:\/\/.*\/lead\/\d+/
        assert result.links["calls"] ==~ /http[s]?:\/\/.*\/lead\/\d+\/calls/
        assert result.links["smses"] ==~ /http[s]?:\/\/.*\/lead\/\d+\/smses/
        assert result.links["emails"] ==~ /http[s]?:\/\/.*\/lead\/\d+\/emails/

        assert result.sourceId == lead.getSourceId()
        assert (result.source ?: "") == (lead.source ?: "")
        assert result.status == status

        assert result.client.name == lead.client.name
        assert result.client.email == lead.client.email
        assert (result.client.phone ?: '') == (lead.client.phone ?: '')
        assert (result.client.address ?: '') == (lead.client.address ?: '')

        assert result.service.name == lead.service.name
        assert result.service.address == lead.service.address
        assert result.service.budget == lead.service.budget
        assert result.service.currency == lead.service.currency
        assert result.service.date == lead.service.date

        assert result.data == (lead.data ?: [:])
        true
    }

    LeadRequest buildLead() {
        new LeadRequest()
                .withClient(new Client()
                .withAddress("26 Blues Brother Drive")
                .withEmail("jake@test.com")
                .withName("Jake Blues")
                .withPhone("+447879440894"))
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

    LeadRequest buildAltLead() {
        new LeadRequest()
                .withClient(new Client()
                .withAddress("62 Blues Brother Drive")
                .withEmail("elwood@test.com")
                .withName("Elwood Blues")
                .withPhone("+447879440893"))
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
