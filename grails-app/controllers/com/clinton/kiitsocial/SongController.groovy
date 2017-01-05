package com.clinton.kiitsocial

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMIN', 'ROLE_USER'])
@Transactional(readOnly = true)
class SongController {

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Song.list(params), model:[songCount: Song.count()]
    }

    def show(Song song) {
        respond song
    }

    @Transactional
    def save(Song song) {
        if (song == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        if (song.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond song.errors, view:'create'
            return
        }

        song.save flush:true

        respond song, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Song song) {
        if (song == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        if (song.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond song.errors, view:'edit'
            return
        }

        song.save flush:true

        respond song, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Song song) {

        if (song == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        song.delete flush:true

        render status: NO_CONTENT
    }
}
