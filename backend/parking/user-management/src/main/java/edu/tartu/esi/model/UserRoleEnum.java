package edu.tartu.esi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum UserRoleEnum {
    USER("User", 1),
    LANDLORD("Landlord", 2),
    ADMIN("Admin", 3);

    private String roleName;
    private int roleId;

    public static UserRoleEnum getRoleByName(String roleName) {
        if (roleName.equals(USER.getRoleName())) {
            return USER;
        } else if (roleName.equals(LANDLORD.getRoleName())) {
            return LANDLORD;
        } else {
            return ADMIN;
        }
    }

    public static UserRoleEnum getRoleById(int roleId) {
        return switch (roleId) {
            case 1 -> USER;
            case 2 -> LANDLORD;
            case 3 -> ADMIN;
            default -> throw new IllegalStateException("Unexpected value: " + roleId);
        };
    }
}