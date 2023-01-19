package com.company.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "pacientes")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPaciente;

    @Column(nullable = false, length = 70)
    private String nombres;

    @Column(nullable = false, length = 70)
    private String apellidos;

    @Column(nullable = false, length = 8)
    private String dni;

    @Column(nullable = false, length = 70)
    private String direccion;

    @Column(nullable = false, length = 9)
    private String telefono;

    @Column(nullable = false, length = 70)
    private String email;
}
