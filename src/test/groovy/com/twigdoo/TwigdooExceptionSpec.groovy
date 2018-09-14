package com.twigdoo

import spock.lang.Specification

class TwigdooExceptionSpec extends Specification {

    def "I can create an exception with just a message"() {
        when:
        TwigdooException e = new TwigdooException("error message")

        then:
        e.message == "error message"
    }

    def "I can create an exception with just a cause"() {
        given:
        IllegalArgumentException cause = new IllegalArgumentException();
        when:
        TwigdooException e = new TwigdooException(cause)

        then:
        e.cause == cause
    }

}
