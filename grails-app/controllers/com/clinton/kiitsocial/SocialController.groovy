package com.clinton.kiitsocial

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMIN', 'ROLE_USER'])
@Transactional(readOnly = true)
class SocialController {

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Social.list(params), model:[socialCount: Social.count()]
    }

    def show(Social social) {
        respond social
    }

    @Transactional
    def save(Social social) {
        if (social == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        if (social.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond social.errors, view:'create'
            return
        }

        social.save flush:true

        respond social, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Social social) {
        if (social == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        if (social.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond social.errors, view:'edit'
            return
        }

        social.save flush:true

        respond social, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Social social) {

        if (social == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        social.delete flush:true

        render status: NO_CONTENT
    }
}
