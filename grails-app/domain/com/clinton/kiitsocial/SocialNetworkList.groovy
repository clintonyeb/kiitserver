package com.clinton.kiitsocial

enum SocialNetworkList {

    FACEBOOK('facebook'),
    INSTAGRAM('instagram'),
    TWEETER('tweeter'),
    SNAPCHAT('snapchat'),
    LINKLEDEN("linkleden")

    final String value
    SocialNetworkList(String value){
        this.value = value
    }

    String getValue(){
        value
    }

    String value(){
        value
    }

    String getKey(){
        name()
    }

    static SocialNetworkList byId(String soc){
        values().find{
            it.value == soc
        }
    }
}
