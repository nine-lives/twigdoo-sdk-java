package com.twigdoo.util

import com.twigdoo.Client
import spock.lang.Specification

class RequestParameterMapperSpec extends Specification {

    private final RequestParameterMapper mapper = new RequestParameterMapper()

    def "I can get a map of the request parameters"() {
        given:
        Client request = new Client()
                .withName("vname")
                .withPhone("vphone")
        when:
        Map<String, String> parameters = mapper.writeToMap(request)

        then:
        parameters['name'] == "vname"
        parameters['phone'] == "vphone"
        parameters.size() == 2
    }

    def "I can write to query parameters"() {
        given:
        Client request = new Client()
                .withName("vname")
                .withPhone("vphone")
        when:
        String query = new RequestParameterMapper().write(request)

        then:
        query == "?name=vname&phone=vphone"
    }

    def "Query parameters is empty string if no values are set"() {
        given:
        Client request = new Client()

        when:
        String query = mapper.write(request)

        then:
        query == ""
    }

    def "I can convert a query string back to a request object"() {
        given:
        String query = "https://api.twigloo.com/lead?name=vname&phone=vphone"

        when:
        Client request = mapper.read(new URL(query), Client);
        Map<String, String> parameters = new RequestParameterMapper().writeToMap(request)

        then:
        parameters['name'] == "vname"
        parameters['phone'] == "vphone"
        parameters.size() == 2
    }

    def "I can modify a mapped request object"() {
        given:
        String query = "https://api.twigloo.com/lead?name=vname&phone=vphone"

        when:
        Client request = mapper.read(new URL(query), Client);
        request.withName("xname")
        Map<String, String> parameters = new RequestParameterMapper().writeToMap(request)

        then:
        parameters['name'] == "xname"
        parameters['phone'] == "vphone"
        parameters.size() == 2
    }
}
