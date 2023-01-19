package com.company.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "menu")
@Data
public class Menu {

    @Id
    private Integer idMenu;

    @Column(nullable = false, length = 30)
    private String icono;

    @Column(nullable = false, length = 30)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String url;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "menu_rol", joinColumns = @JoinColumn(name = "id_menu", referencedColumnName = "idMenu"),
            inverseJoinColumns = @JoinColumn(name = "id_rol", referencedColumnName = "idRol"))
    private List<Rol> roles;

}
