package edu.tartu.esi.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Role {

    ADMIN,
    LANDLORD,
    USER;
}
