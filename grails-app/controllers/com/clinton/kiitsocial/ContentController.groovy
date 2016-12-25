package com.clinton.kiitsocial

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ContentController {

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Content.list(params), model:[contentCount: Content.count()]
    }

    def show(Content content) {
        respond content
    }

    @Transactional
    def save(Content content) {
        if (content == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        if (content.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond content.errors, view:'create'
            return
        }

        content.save flush:true

        respond content, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Content content) {
        if (content == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        if (content.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond content.errors, view:'edit'
            return
        }

        content.save flush:true

        respond content, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Content content) {

        if (content == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        content.delete flush:true

        render status: NO_CONTENT
    }
}
