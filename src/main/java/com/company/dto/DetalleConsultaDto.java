package com.company.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DetalleConsultaDto {

    private Integer idDetalleConsulta;

    @JsonIgnore
    private ConsultaDto consultaDto;

    @NotNull
    private String diagnostico;

    @NotNull
    private String tratamiento;

}
