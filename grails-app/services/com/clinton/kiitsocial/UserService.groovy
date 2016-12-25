package com.clinton.kiitsocial

import com.clinton.auth.Role
import com.clinton.auth.User
import com.clinton.auth.UserRole
import grails.transaction.Transactional

@Transactional
class UserService {

    User registerUser(props){
        User user = new User(props)
        user.profile = new Profile(user: user)
        user.save(flush: true)
        Role role = Role.findOrSaveByAuthority(props.role)
        UserRole.create(user, role, true)
        user
    }

    User updateProfile(id, prof){
        User user = User.get(id)
        user.profile.properties = prof
    }
}
