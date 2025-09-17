package com.nocturno.api.models.user;

public enum Status {
    ACTIVE("active"), DELETED("deleted"), BANNED("banned");

    private final String value;

    Status(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
