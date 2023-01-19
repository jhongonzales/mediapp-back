package com.company.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "medicos")
@Data
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMedico;

    @Column(nullable = false, length = 70)
    private String nombres;

    @Column(nullable = false, length = 70)
    private String apellidos;

    @Column(nullable = false, length = 70, unique = true)
    private String cmp;

    @Column(nullable = true, length = 70)
    private String fotoUrl;

}
