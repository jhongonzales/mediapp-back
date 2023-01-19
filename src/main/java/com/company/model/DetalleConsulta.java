package com.company.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "detalle_consulta")
@Data
public class DetalleConsulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDetalle;

    @ManyToOne
    @JoinColumn(name = "id_consulta", nullable = false, foreignKey = @ForeignKey(name = "FK_detalle_consulta_consulta"))
    private Consulta consulta;

    @Column(nullable = false, length = 70)
    private String diagnostico;

    @Column(nullable = false, length = 70)
    private String tratamiento;


}
