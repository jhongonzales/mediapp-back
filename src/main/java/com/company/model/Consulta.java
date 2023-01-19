package com.company.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "consuta")
@Data
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idConsulta;

    @ManyToOne
    @JoinColumn(name = "id_paciente", nullable = false, foreignKey = @ForeignKey(name = "FK_consulta_paciente"))
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "id_medico", nullable = false, foreignKey = @ForeignKey(name = "FK_consulta_medico"))
    private Medico medico;

    @ManyToOne
    @JoinColumn(name = "id_especialidad", nullable = false, foreignKey = @ForeignKey(name = "FK_consulta_especialidad"))
    private Especialidad especialidad;

    @Column(name = "num_consultorio", nullable = false, length = 3)
    private String numConsultorio;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @OneToMany(mappedBy = "consulta", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<DetalleConsulta> detalleConsulta;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Consulta consulta = (Consulta) o;
        return Objects.equals(idConsulta, consulta.idConsulta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idConsulta);
    }
}
