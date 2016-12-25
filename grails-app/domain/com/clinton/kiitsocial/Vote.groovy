package com.clinton.kiitsocial

import com.clinton.auth.User

class Vote {

    Integer upTotal = 0
    Integer downTotal = 0
    Date dateCreated

    static belongsTo = [user: User, content: Content]

    static constraints = {
    }
}
