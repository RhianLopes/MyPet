package com.mypet.MyPet.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Notification extends Domain  {

    private int notifiedPetId;

    private int recivedPetId;

    //TODO: ALTERAR DE STRING PARA UM ENUM ESPECIFICO
    private String message;
}
