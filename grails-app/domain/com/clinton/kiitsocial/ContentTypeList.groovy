package com.clinton.kiitsocial

enum ContentTypeList {
    ANNOUNCEMENT("announcement"),
    ARTICLE("article"),
    EVENT("event"),
    TWEET("tweet")

    private final String value

    ContentTypeList(String value){this.value = value}

    String getValue(){
        value
    }

    static ContentTypeList byId(String val){
        values().find{
            it.value == val
        }
    }

    String toString() { String.valueOf(value)}
    String getKey() { name() }
}