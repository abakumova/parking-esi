package edu.tartu.esi.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Role {

    ADMIN,
    LANDLORD,
    USER;
}
