package com.company.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "consulta_examen")
@IdClass(ConsultaExamenPk.class)
@Data
public class ConsultaExamen {

    @Id
    private Consulta consulta;

    @Id
    private Examen examen;


}
