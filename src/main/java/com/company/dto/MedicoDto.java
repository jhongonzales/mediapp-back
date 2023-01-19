package com.company.dto;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class MedicoDto {

    private Integer idMedico;

    @Size(min=2, message = "Nombres debe tener mínimo 2 caracteres")
    private String nombres;

    @Size(min=2, message = "Apellidos debe tener mínimo 2 caracteres")
    private String apellidos;

    private String cmp;
    private String fotoUrl;

}

