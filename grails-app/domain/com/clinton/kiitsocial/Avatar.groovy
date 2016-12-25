package com.clinton.kiitsocial

class Avatar {

    byte[] avatarBytes
    String avatarType
    String avatarName
    Date dateCreated
    Date lastUpdated

    static constraints = {
        avatarBytes maxSize: 16384 //16KB
    }
}
