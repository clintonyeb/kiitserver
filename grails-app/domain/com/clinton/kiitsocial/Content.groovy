package com.clinton.kiitsocial

import com.clinton.auth.User

class Content {

    String text
    Date dateCreated
    Date lastUpdated
    boolean flagged = false

    static hasMany = [tags: Tag, avatars: Avatar]

    static belongsTo = [user: User]
    static hasOne = [vote: Vote]

    static constraints = {
        text blank: false, minSize:10, maxSize:100
        tags nullable: true
        vote nullable: false
    }
}
