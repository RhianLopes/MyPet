package com.mypet.MyPet.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends Domain{

    private String name;

    private String nickname;

    @Email
    private String email;

    private String password;

    private String photo;

    public Profile getProfile() {
        return Profile.USUARIO;
    }
}
