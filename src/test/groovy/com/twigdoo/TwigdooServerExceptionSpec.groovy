package com.twigdoo

import spock.lang.Specification

class TwigdooServerExceptionSpec extends Specification {

    def "I can construct the exception"() {
        given:
        TwigdooError error = new TwigdooError("error_message")

        when:
        TwigdooServerException e = new TwigdooServerException(401, 'Unauthorised', error)

        then:
        e.statusCode == 401
        e.statusMessage == 'Unauthorised'
        e.error.message == 'error_message'
    }
}
