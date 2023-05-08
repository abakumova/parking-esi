package edu.tartu.esi.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_DELETE("admin:delete"),
    ADMIN_CREATE("admin:create"),
    LANDLORD_READ("landlord:read"),
    LANDLORD_UPDATE("landlord:update"),
    LANDLORD_DELETE("landlord:delete"),
    LANDLORD_CREATE("landlord:create"),
    USER_READ("user:read"),
    USER_UPDATE("user:update"),
    USER_DELETE("user:delete"),
    USER_CREATE("user:create");

    @Getter
    private final String permission;
}
