package com.clinton.kiitsocial

class Event extends Content {
    String title
    String venue
    Date dateOfEvent
    ContentTypeList contentType = ContentTypeList.EVENT

    static constraints = {
        title size: 5..100
        venue maxSize: 500
        dateOfEvent blank: false, validator: {
            dateOfEvent > new Date()
        }
    }
}