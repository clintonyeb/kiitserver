package com.clinton.auth

import com.clinton.kiitsocial.Avatar
import com.clinton.kiitsocial.AvatarService
import com.clinton.kiitsocial.GenderList
import grails.test.mixin.*
import spock.lang.*
import static org.springframework.http.HttpStatus.*

@TestFor(UserController)
@Mock([User, Avatar, AvatarService])
class UserControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        params['username'] = 'clinton'
        params['password'] = 'mypass'
        params['nickname'] = 1233444
        params['gender'] = GenderList.MALE
    }

    void "Test the index action returns the correct response"() {

        when:"The index action is executed"
            controller.index()

        then:"The response is correct"
            response.text == '[]'
    }


    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = JSON_CONTENT_TYPE
            request.method = 'POST'
            def user = new User()
            user.validate()
            controller.save(user)

        then:"The create view is rendered again with the correct model"
            response.status == UNPROCESSABLE_ENTITY.value()
            response.json.errors

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            user = new User(params)

            controller.save(user)

        then:"A redirect is issued to the show action"
            User.count() == 1
            response.status == CREATED.value()
            response.json

        when: "The persisted user is retrieved"
            User us = User.get(1)

        then:
            us.username == params.username
            us.password == params.password
            us.smallAvatar
            us.smallAvatar.avatarBytes.size() > 0
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show()

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            response.reset()
            def user= new User(params).save()
            controller.show()

        then:"A model is populated containing the domain instance"
            user!= null
            response.status == OK.value()
            response.json
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = JSON_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.status == NOT_FOUND.value()

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def user= new User()
            user.validate()
            controller.update(user)

        then:"The edit view is rendered again with the invalid instance"
            response.status == UNPROCESSABLE_ENTITY.value()
            response.json.errors

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            user= new User(params).save(flush: true)
            controller.update(user)

        then:"A redirect is issued to the show action"
            user!= null
            response.status == OK.value()
            response.json.id == user.id
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = JSON_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.status == NOT_FOUND.value()


        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def user= new User(params).save(flush: true)

        then:"It exists"
            User.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(user)

        then:"The instance is deleted"
            User.count() == 0
            response.status == NO_CONTENT.value()
            
    }
}