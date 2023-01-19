package com.company.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "rol")
@Data
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRol;

    @Column(nullable = false, length = 30)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String descripcion;
}

