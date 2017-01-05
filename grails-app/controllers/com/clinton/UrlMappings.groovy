package com.clinton

class UrlMappings {

    static mappings = {
        "/api/tweets"(resources: "tweet")
        "/api/guests/users"(resources: "user")
        "/api/users"(resources: "user")
        "/api/avatars/$size"(controller: "avatar", action: "saveAvatar")
        "/api/avatars/$id/$size?"(controller: "avatar", action: "showAvatar")
        "/api/announcements"(resources: "announcement")
        "/api/articles"(resources: "article")
        "/api/votes"(resources: "vote")
        "/api/events"(resources: "event")
        "/api/profiles"(resources: "profile")
        "/api/social-networks"(resources: "social")
        "/api/tags"(resources: "tag")
        "/api/songs"(resources: "song")

        "/"(controller: 'application', action:'index')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
