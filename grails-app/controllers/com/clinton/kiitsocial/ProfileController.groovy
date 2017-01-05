package com.clinton.kiitsocial

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMIN', 'ROLE_USER'])
@Transactional(readOnly = true)
class ProfileController {

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Profile.list(params), model:[profileCount: Profile.count()]
    }

    def show(Profile profile) {
        respond profile
    }

    @Transactional
    def save(Profile profile) {
        if (profile == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        if (profile.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond profile.errors, view:'create'
            return
        }

        profile.save flush:true

        respond profile, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Profile profile) {
        if (profile == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        if (profile.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond profile.errors, view:'edit'
            return
        }

        profile.save flush:true

        respond profile, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Profile profile) {

        if (profile == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        profile.delete flush:true

        render status: NO_CONTENT
    }
}
