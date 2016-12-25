package com.clinton.kiitsocial

import com.clinton.auth.User

class Social {

    SocialNetworkList socialName
    String socialUrl

    static belongsTo = [user: User]

    static constraints = {
        socialName blank: false, nullable: false
        socialUrl url: true, blank: false
    }
}
