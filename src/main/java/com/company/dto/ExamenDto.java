package com.company.dto;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class ExamenDto {

    private Integer idExamen;

    @Size(min=5, message = "Nombre debe tener mínimo 5 caracteres")
    private String nombre;

    @Size(min=5, message = "Descripción debe tener mínimo 5 caracteres")
    private String descripcion;

}
