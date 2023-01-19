package com.company.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {

    @Id
    private Integer idUsuario;

    @Column(name = "nombre", nullable = false, length = 70, unique = true)
    private String username;

    @Column(name = "clave", nullable = false, length = 30)
    private String password;

    @Column(name = "estado", nullable = false, length = 1)
    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_rol", joinColumns = @JoinColumn(name = "id_usuario",
            referencedColumnName = "idUsuario"), inverseJoinColumns = @JoinColumn(name = "id_rol", referencedColumnName = "idRol"))
    private List<Rol> roles;

}
