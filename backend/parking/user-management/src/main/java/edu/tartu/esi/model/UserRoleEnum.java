package edu.tartu.esi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static edu.tartu.esi.model.UserPermission.*;

//@Getter
//@AllArgsConstructor
//@NoArgsConstructor
public enum UserRoleEnum {

    USER(Set.of(READ_OWN_DATA, WRITE_OWN_DATA)),
    LANDLORD(Set.of(READ_OWN_DATA, WRITE_OWN_DATA)),
    ADMIN(Set.of(READ_OWN_DATA, WRITE_OWN_DATA, READ_USERS_DATA, WRITE_USERS_DATA, READ_LANDLORDS_DATA, WRITE_LANDLORDS_DATA));
    private final Set<UserPermission> permissions;

    UserRoleEnum(Set<UserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<UserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(UserPermission::getPermission)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }

    @JsonCreator
    public static UserRoleEnum forValue(String value) {
        return valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}