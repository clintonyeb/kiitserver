package com.clinton.kiitsocial

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMIN'])
@Transactional(readOnly = true)
class TweetController {

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Tweet.list(params), model:[tweetCount: Tweet.count()]
    }

    def show(Tweet tweet) {
        respond tweet
    }

    @Transactional
    def save(Tweet tweet) {
        if (tweet == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        if (tweet.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond tweet.errors, view:'create'
            return
        }

        tweet.save flush:true

        respond tweet, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Tweet tweet) {
        if (tweet == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        if (tweet.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond tweet.errors, view:'edit'
            return
        }

        tweet.save flush:true

        respond tweet, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Tweet tweet) {

        if (tweet == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        tweet.delete flush:true

        render status: NO_CONTENT
    }
}
