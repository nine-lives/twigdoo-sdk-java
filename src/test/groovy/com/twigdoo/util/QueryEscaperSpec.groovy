package com.twigdoo.util

import spock.lang.Specification
import spock.lang.Unroll

class QueryEscaperSpec extends Specification {

    @Unroll("I can escape a query string - #query")
    def "I can escape a query string"() {
        when:
        String escaped = QueryEscaper.escape(query)

        then:
        escaped == expected

        where:
        query  | expected
        "a+z"  | "a\\+z"
        "a-z"  | "a\\-z"
        "a=z"  | "a\\=z"
        "a&&z" | "a\\&&z"
        "a||z" | "a\\||z"
        "a>z"  | "az"
        "a<z"  | "az"
        "a!z"  | "a\\!z"
        "a(z"  | "a\\(z"
        "a)z"  | "a\\)z"
        "a{z"  | "a\\{z"
        "a}z"  | "a\\}z"
        "a]z"  | "a\\]z"
        "a[z"  | "a\\[z"
        "a^z"  | "a\\^z"
        "a\"z" | "a\\\"z"
        "a~z"  | "a\\~z"
        "a*z"  | "a\\*z"
        "a?z"  | "a\\?z"
        "a:z"  | "a\\:z"
        "a\\z" | "a\\\\z"
        "a/z"  | "a\\/z"
    }

    def "I can escape a null query string"() {
        when:
        String escaped = QueryEscaper.escape(null)

        then:
        escaped == null
    }


    def "The constructor is private"() {
        when:
        QueryEscaper escaper = new QueryEscaper()

        then:
        escaper
    }
}