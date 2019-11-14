package com.mypet.MyPet.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Profile {

    USUARIO("ROLE_USUARIO");

    private final String role;
}
