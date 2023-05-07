package edu.tartu.esi.model;

import lombok.*;

@NoArgsConstructor
public enum UserPermission {

    READ_OWN_DATA("user:read"),
    WRITE_OWN_DATA("user:write"),
    READ_USERS_DATA("users:read"),
    WRITE_USERS_DATA("users:write"),
    READ_LANDLORDS_DATA("landlords:read"),
    WRITE_LANDLORDS_DATA("landlords:write");

    private String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
