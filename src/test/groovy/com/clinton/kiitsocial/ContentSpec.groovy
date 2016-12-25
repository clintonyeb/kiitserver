package com.clinton.kiitsocial

import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Content)
class ContentSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    @Unroll("text: #text")
    void "test text"() {
        setup:
        def content = new Content(text: text)

        expect:
        content.validate(['text']) == validated

        where:
        text                                             | validated
        '1.This is a content if you can see me gg'       | true
        null                                             | false
        '2.This is a content if you can see me'          | true
        '3.This is a content if you can see me hh'       | true
        '4.This'                                         | false
        '5.This is a content if you can see me dsv sfe rege fererge wfefer efege eferfergre ererg egeger eege egege'  | false
    }
}
