package com.clinton.kiitsocial

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class AnnouncementController {

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Announcement.list(params), model:[announcementCount: Announcement.count()]
    }

    def show(Announcement announcement) {
        respond announcement
    }

    @Transactional
    def save(Announcement announcement) {
        if (announcement == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        if (announcement.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond announcement.errors, view:'create'
            return
        }

        announcement.save flush:true

        respond announcement, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Announcement announcement) {
        if (announcement == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        if (announcement.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond announcement.errors, view:'edit'
            return
        }

        announcement.save flush:true

        respond announcement, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Announcement announcement) {

        if (announcement == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        announcement.delete flush:true

        render status: NO_CONTENT
    }
}
