package com.company.dto;


import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ConsultaListaExamenDto {

    @NotNull
    private ConsultaDto consultaDto;

    @NotNull
    private List<ExamenDto> lstExamen;

}
