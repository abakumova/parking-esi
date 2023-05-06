package edu.tartu.esi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

import static edu.tartu.esi.model.UserPermission.*;

@Getter
//@AllArgsConstructor
@NoArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum UserRoleEnum {

    USER(Set.of(READ_OWN_DATA, WRITE_OWN_DATA)),
    LANDLORD(Set.of(READ_OWN_DATA, WRITE_OWN_DATA)),
    ADMIN(Set.of(READ_OWN_DATA, WRITE_OWN_DATA, READ_USERS_DATA, WRITE_USERS_DATA, READ_LANDLORDS_DATA, WRITE_LANDLORDS_DATA));
    private Set<UserPermission> permissions;

    UserRoleEnum(Set<UserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<UserPermission> getPermissions() {
        return permissions;
    }

//    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
//        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
//                .map(UserPermission::getPermission)
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toSet());
//        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
//        return permissions;
//    }

    @JsonCreator
    public static UserRoleEnum forValue(String value) {
        return valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}