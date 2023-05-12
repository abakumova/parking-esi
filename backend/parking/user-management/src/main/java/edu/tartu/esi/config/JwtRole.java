package edu.tartu.esi.config;

import edu.tartu.esi.model.Role;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAnyRole(@JwtRole.roles)")
public @interface JwtRole {
    Role[] roles();
}