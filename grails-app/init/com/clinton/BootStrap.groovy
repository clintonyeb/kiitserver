package com.clinton

import com.clinton.auth.Role
import com.clinton.auth.User
import com.clinton.auth.UserRole
import com.clinton.kiitsocial.GenderList

class BootStrap {

    def init = { servletContext ->
        def user = new User(username: 'clinton', password: 'password', uniqueId: 1356881, gender: GenderList.MALE)
                .save(failOnError: true)
        def userRole = Role.findOrSaveByAuthority("ROLE_USER")
        UserRole.create(user, userRole, true)

        assert User.count == 1
        assert UserRole.count == 1

    }
    def destroy = {
    }
}
