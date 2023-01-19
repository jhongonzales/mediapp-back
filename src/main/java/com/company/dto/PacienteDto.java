package com.company.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
public class PacienteDto {

    private Integer idPaciente;

    @Size(min=3, message = "Nombres debe tener mínimo 3 caracteres")
    private String nombres;

    @Size(min=3, message = "Apellidos debe tener mínimo 3 caracteres")
    private String apellidos;

    @Size(min=8,max=8, message = "DNI debe tener 8 caracteres")
    private String dni;

    @Size(min = 3)
    private String direccion;

    @Size(min=9,max=9, message = "Número de celular debe tener 9 caracteres")
    private String telefono;

    @Email
    private String email;

}
