package com.clinton.kiitsocial

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMIN', 'ROLE_USER'])
@Transactional(readOnly = true)
class TagController {

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Tag.list(params), model:[tagCount: Tag.count()]
    }

    def show(Tag tag) {
        respond tag
    }

    @Transactional
    def save(Tag tag) {
        if (tag == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        if (tag.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond tag.errors, view:'create'
            return
        }

        tag.save flush:true

        respond tag, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Tag tag) {
        if (tag == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        if (tag.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond tag.errors, view:'edit'
            return
        }

        tag.save flush:true

        respond tag, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Tag tag) {

        if (tag == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        tag.delete flush:true

        render status: NO_CONTENT
    }
}
