package com.twigdoo.util

import spock.lang.Specification

class RequestParameterMapperSpec extends Specification {

    private final RequestParameterMapper mapper = new RequestParameterMapper()


    def "I can get a map of the request parameters"() {
        given:
        TestRequest request = new TestRequest(obj: 'value', arr: ['one', 'two'])

        when:
        Map<String, String> parameters = mapper.writeToMap(request)

        then:
        parameters['obj'] == "value"
        parameters['arr'] == "one,two"
        parameters.size() == 2
    }

    def "I can write to query parameters"() {
        given:
        TestRequest request = new TestRequest(obj: 'value', arr: ['one', 'two'])

        when:
        String query = new RequestParameterMapper().write(request)

        then:
        query == "?obj=value&arr=one%2Ctwo"
    }


    def "Query parameters is empty string if no values are set"() {
        given:
        TestRequest request = new TestRequest()

        when:
        String query = mapper.write(request)

        then:
        query == ""
    }

    def "I can convert a query string back to a request object"() {
        given:
        String query = "https://api.twigloo.com/lead?obj=value&arr[]=one,two"

        when:
        TestRequest request = mapper.read(new URL(query), TestRequest);
        Map<String, String> parameters = new RequestParameterMapper().writeToMap(request)

        then:
        parameters['obj'] == "value"
        parameters['arr'] == 'one,two'
        parameters.size() == 2
    }

    def "I get an error when I try to load it into an invalid object"() {
        given:
        String query = "https://api.twigloo.com/lead?obj=value&arr[]=one,two"

        when:
        mapper.read(new URL(query), String);

        then:
        IllegalStateException e = thrown(IllegalStateException)
    }


    def "I can modify a mapped request object"() {
        given:
        String query = "https://api.twigloo.com/lead?obj=value&arr[]=one%2Ctwo"

        when:
        TestRequest request = mapper.read(new URL(query), TestRequest);
        request.obj = "new"
        request.arr = ["three", "four"]
        Map<String, String> parameters = new RequestParameterMapper().writeToMap(request)

        then:
        parameters['obj'] == "new"
        parameters['arr'] == "three,four"
        parameters.size() == 2
    }
}
