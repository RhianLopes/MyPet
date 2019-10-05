package com.mypet.MyPet.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Event extends Domain {

    private Pet pet;

    private String nome;

    private String description;

    private BigDecimal latitude;

    private BigDecimal longitude;
}
