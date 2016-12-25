package com.clinton.kiitsocial

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class VoteController {

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Vote.list(params), model:[voteCount: Vote.count()]
    }

    def show(Vote vote) {
        respond vote
    }

    @Transactional
    def save(Vote vote) {
        if (vote == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        if (vote.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond vote.errors, view:'create'
            return
        }

        vote.save flush:true

        respond vote, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Vote vote) {
        if (vote == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        if (vote.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond vote.errors, view:'edit'
            return
        }

        vote.save flush:true

        respond vote, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Vote vote) {

        if (vote == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        vote.delete flush:true

        render status: NO_CONTENT
    }
}
