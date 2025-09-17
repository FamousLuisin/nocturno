package com.nocturno.api.models.user;

public enum Role{
    ADMIN("admin"), USER("user"), MODERATOR("moderator");

    private final String value;

    Role(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
