package com.company.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "examen")
@Data
public class Examen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idExamen;

    @Column(nullable = false, length = 70)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String descripcion;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Examen examen = (Examen) o;
        return Objects.equals(idExamen, examen.idExamen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idExamen);
    }
}
