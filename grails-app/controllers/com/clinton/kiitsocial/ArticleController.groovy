package com.clinton.kiitsocial

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMIN', 'ROLE_USER'])
@Transactional(readOnly = true)
class ArticleController {

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Article.list(params), model:[articleCount: Article.count()]
    }

    def show(Article article) {
        respond article
    }

    @Transactional
    def save(Article article) {
        if (article == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        if (article.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond article.errors, view:'create'
            return
        }

        article.save flush:true

        respond article, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Article article) {
        if (article == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        if (article.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond article.errors, view:'edit'
            return
        }

        article.save flush:true

        respond article, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Article article) {

        if (article == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        article.delete flush:true

        render status: NO_CONTENT
    }
}
