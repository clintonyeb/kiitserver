package com.clinton.auth

import com.clinton.kiitsocial.Avatar
import com.clinton.kiitsocial.Content
import com.clinton.kiitsocial.GenderList
import com.clinton.kiitsocial.Profile
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode(includes = 'username')
@ToString(includes = 'username', includeNames = true, includePackage = false)
class User implements Serializable {

    private static final long serialVersionUID = 1

    transient springSecurityService

    String username
    String password
    boolean enabled = true
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired

    //starting custom domain...can touch this
    Integer uniqueId
    Date dateCreated
    Date lastUpdated
    Avatar smallAvatar
    GenderList gender

    static hasMany = [contents: Content]

    static hasOne = [profile: Profile]

    User(String username, String password) {
        this()
        this.username = username
        this.password = password
    }

    Set<Role> getAuthorities() {
        UserRole.findAllByUser(this)*.role
    }

    def beforeInsert() {
        encodePassword()
    }

    def beforeUpdate() {
        if (isDirty('password')) {
            encodePassword()
        }
    }

    protected void encodePassword() {
        password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
    }

    static transients = ['springSecurityService']

    static constraints = {
        username blank: false, unique: true
        password blank: false, minSize: 5, validator: { pass, user ->
            pass != user.username
        }
        uniqueId blank: false, unique: true, range: 1000..2000000000

        profile unique: true, nullable: true
        contents nullable: true
        smallAvatar nullable: true
    }

    static mapping = {
        password column: '`password`'
    }
}
