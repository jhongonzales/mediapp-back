package com.company.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ConsultaDto {

    private Integer idConsulta;

    @NotNull(message = "El paciente no puede ser null")
    private PacienteDto paciente;

    @NotNull(message = "El médico no puede ser null")
    private MedicoDto medico;

    @NotNull(message = "La especialidad no puede ser null")
    private EspecialidadDto especialidad;

    @NotNull(message = "El numConsultorio no puede ser null")
    private String numConsultorio;

    @NotNull(message = "La fecha no puede ser null")
    private LocalDateTime fecha;

    @NotNull
    private List<DetalleConsultaDto> detalleConsulta;

}
