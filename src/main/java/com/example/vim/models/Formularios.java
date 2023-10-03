package com.example.vim.models;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="formularios")
public class Formularios {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY) // Que se genere automaticamente el id con el AutoIncrement
    private Long id_formulario;

    @Column(name="id_evento")
    private int id_evento;

    @Column(name="nombre_formulario")
    private String nombre_formulario;

    @Column(name="informacion_formulario")
    private String informacion_formulario;

    @Column(name="cupo_maximo")
    private Integer cupo_maximo;

    @Column(name="contador_cupo")
    private Integer contador_cupo;

    @Column(name="contador_edicion")
    private Integer contador_edicion;

    @Column(name="numero_preguntas")
    private Integer numero_preguntas;

    @Column(name="estructura_formulario")
    private String estructura_formulario;
}
