package com.clinton.kiitsocial

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import org.springframework.web.multipart.MultipartFile

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMIN', 'ROLE_USER'])
@Transactional(readOnly = true)
class AvatarController {

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def avatarService

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Avatar.list(params), model:[avatarCount: Avatar.count()]
    }

    def show(Avatar avatar) {
        respond avatar
    }

    @Transactional
    def save(Avatar avatar) {
        if (avatar == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        if (avatar.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond avatar.errors, view:'create'
            return
        }

        avatar.save flush:true

        respond avatar, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Avatar avatar) {
        if (avatar == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        if (avatar.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond avatar.errors, view:'edit'
            return
        }

        avatar.save flush:true

        respond avatar, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Avatar avatar) {

        if (avatar == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        avatar.delete flush:true

        render status: NO_CONTENT
    }

    def showAvatar() {
        try {
            response.status = AvatarService.responseCode
            avatarService.showAvatar((long)params.id, response)
        }catch (Exception e){
            System.err.print(e)
        }

    }

    def saveAvatar(){
        MultipartFile avatar = request.getFile('avatar')
        if (!avatar) return
        String size = params.size
        switch (Integer.parseInt(size)){
            case  AvatarService.AVATAR_TINY:
                avatar ? respond (avatarService.createAvatar(avatar, AvatarService.AVATAR_TINY)):
                        respond (500)
                break
            case AvatarService.AVATAR_SMALL:
                avatar ? respond (avatarService.createAvatar(avatar, AvatarService.AVATAR_TINY)):
                        respond (500)
                break
            case AvatarService.AVATAR_MEDIUM:
                avatar ? respond (avatarService.createAvatar(avatar, AvatarService.AVATAR_TINY)):
                        respond (500)
                break
            default:
                throw new IllegalArgumentException()
        }
    }
}
