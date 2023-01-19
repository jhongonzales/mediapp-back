package com.company.model;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
public class ConsultaExamenPk implements Serializable{
    @ManyToOne
    @JoinColumn(name = "id_consulta", nullable = false, foreignKey = @ForeignKey(name = "FK_consulta_examen_consulta"))
    private Consulta consulta;

    @ManyToOne
    @JoinColumn(name = "id_examen", nullable = false, foreignKey = @ForeignKey(name = "FK_consulta_examen_examen"))
    private Examen examen;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConsultaExamenPk that = (ConsultaExamenPk) o;
        return Objects.equals(consulta, that.consulta) && Objects.equals(examen, that.examen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(consulta, examen);
    }
}
