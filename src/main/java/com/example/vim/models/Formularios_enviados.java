package com.example.vim.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name="formularios_enviados")
public class Formularios_enviados {

    @Id
    @Getter @Setter @Column(name="id_formulario")
    private Long id_formulario;

    @Getter @Setter @Column(name="username")
    private String username;

    @Getter @Setter @Column(name="fecha")
    private Date date;

    @Getter @Setter @Column(name="estado")
    private String estado;
}
