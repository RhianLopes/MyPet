package com.mypet.MyPet.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class Event extends Domain {

    private Pet pet;

    private String nome;

    private String description;

    private BigDecimal latitude;

    private BigDecimal longitude;
}
