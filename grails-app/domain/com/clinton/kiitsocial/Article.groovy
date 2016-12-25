package com.clinton.kiitsocial

class Article extends Content{
    String title
    ContentTypeList contentType = ContentTypeList.ARTICLE

    static constraints = {
        title size: 5..100
    }
}