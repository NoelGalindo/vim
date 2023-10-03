package com.example.vim.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


public class Solicitud_formularios {
    @Id
    @Getter @Setter @Column(name="id_formulario")
    @GeneratedValue(strategy= GenerationType.IDENTITY) // Que se genere automaticamente el id con el AutoIncrement
    private Long id_formulario;

    @Getter @Setter @Column(name="username")
    private String username;

    @Getter @Setter @Column(name="direccion")
    private String direccion_url;

    @Getter @Setter @Column(name="nombre_formulario")
    private String nombre_formulario;

    @Getter @Setter @Column(name="informacion_formulario")
    private String informacion_formulario;

    @Getter @Setter @Column(name="cupo_maximo")
    private Integer cupo_maximo;

    @Getter @Setter @Column(name="contador_cupo")
    private Integer contador_cupo;

    @Getter @Setter @Column(name="contador_edicion")
    private Integer contador_edicion;

    @Getter @Setter @Column(name="numero_preguntas")
    private Integer numero_preguntas;

    @Getter @Setter @Column(name="status")
    private String status;

    @Getter @Setter @Column(name="estructura_formulario")
    private String estructura_formulario;
}
