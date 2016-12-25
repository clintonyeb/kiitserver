package com.clinton.auth

import com.clinton.kiitsocial.GenderList
import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

@TestFor(User)
class UserSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    @Unroll("username: #username, password: #password, uniqueId: #uniqueId validated: #validated, size: #size, field: #field")
    void "a user object can be created"() {
        setup:
        def user = new User(username: username, password: password, uniqueId: uniqueId)

        expect:
        validated == user.validate(['username', 'password', 'uniqueId'])
        user.errors.errorCount == size
        user?.errors?.fieldError?.field == field

        where:
        username      | password     | uniqueId         | validated     | size  | field
        'clinton'     | 'mypass'     |  12334545        | true          | 0     | null
        null          | 'mypass'     |  12377788        | false         | 1     | 'username'
        'clinton'     | 'clinton'    | 2323433          | false         | 1     | 'password'
        'clinton'     | ''           | 2132232          | false         | 1     | 'password'
        'clinton'     | 'my'         | 323232423        | false         | 1     | 'password'
        'clinton'     | 'mypass'     |  0               | false         | 1     | 'uniqueId'
        'clinton'     | 'mypass'     |  -1              | false         | 1     | 'uniqueId'
    }


    @Unroll("#username, #password, #uniqueId, #validated #gender")
    void "instance of domain can be validated"(){
        expect:
        new User(username: username, password: password, uniqueId: uniqueId, gender: gender)
                .validate(['username', 'password', 'uniqueId', 'gender']) == validated

        where:
        username      | password     | uniqueId     |  gender              | validated
        'clinton'     | 'mypass'     |  12334545    |   GenderList.MALE    | true
        'clinton'     | 'mypass'     |  12334545    |   0                  | true
        'clinton'     | 'mypass'     |  12334545    |   "she"              | false
    }


}