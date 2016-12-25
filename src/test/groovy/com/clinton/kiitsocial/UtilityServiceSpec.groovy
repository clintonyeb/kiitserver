package com.clinton.kiitsocial

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(UtilityService)
class UtilityServiceSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "able to format date"() {
        setup:
        def date = new Date()

        when:
        def res = service.prettyTime(date, Locale.ENGLISH)

        then:
        res
        res == "6s"

    }
}
