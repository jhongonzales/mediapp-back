package com.company.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "especialidades")
@Data
public class Especialidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEspecialidad;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String descripcion;

}
