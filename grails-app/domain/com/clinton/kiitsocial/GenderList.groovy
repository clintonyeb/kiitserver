package com.clinton.kiitsocial

enum GenderList {

    MALE(0),
    FEMALE(1)

    private final int value

    GenderList(int value){this.value = value}

    String getValue(){
        value
    }

    static GenderList byId(int val){
        values().find{
            it.value == val
        }
    }

    String toString() { "${value}"}
    String getKey() { name() }
}
