package com.clinton.kiitsocial

class Tag {
    String tagName

    static belongsTo = [content: Content]

    static constraints = {
        tagName size: 3..15, blank: false, unique: true
    }
}
