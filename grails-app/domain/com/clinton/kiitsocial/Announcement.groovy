package com.clinton.kiitsocial

class Announcement extends Content{
    String title
    ContentTypeList contentType = ContentTypeList.ANNOUNCEMENT

    static constraints = {
        title size: 5..100
    }
}
