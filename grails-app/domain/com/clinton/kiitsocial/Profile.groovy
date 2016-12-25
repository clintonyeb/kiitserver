package com.clinton.kiitsocial

import com.clinton.auth.User

class Profile {

    String bio
    String contact
    String address
    String emailId
    Avatar avatarImage
    User user

    static hasMany = [socialNetworks: Social]

    static constraints = {
        bio nullable: true, maxSize: 50
        address nullable: true, maxSize: 50
        contact nullable: true, matches: "^\\+(?:[0-9] ?){6,14}[0-9]\$"
        socialNetworks nullable: true, unique: true
        emailId email: true, nullable: true
        avatarImage nullable: true
    }
}
