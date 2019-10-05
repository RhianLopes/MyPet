package com.mypet.MyPet.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Notification extends Domain  {

    private int notifiedPetId;

    private int recivedPetId;

    //TODO: ALTERAR DE STRING PARA UM ENUM ESPECIFICO
    private String message;
}
