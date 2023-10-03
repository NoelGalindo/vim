package com.example.vim.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;


public class Example_form {

    @Id
    @Getter @Setter @Column(name="folio")
    @GeneratedValue(strategy= GenerationType.IDENTITY) // Que se genere automaticamente el id con el AutoIncrement
    private long folio;

    @Getter @Setter @Column(name="id_formulario")
    private long id_formulario;

    @Getter @Setter @Column(name="nombre")
    private String nombre;

    @Getter @Setter @Column(name="apellido_p")
    private String apellid_p;

    @Getter @Setter @Column(name="apellido_m")
    private String apellido_m;

    @Getter @Setter @Column(name="sexo")
    private String sexo;

    @Getter @Setter @Column(name="email")
    private String email;

    @Getter @Setter @Column(name="fecha")
    private Timestamp fecha;

    @Getter @Setter @Column(name="voucher")
    private String voucher;

    @Getter @Setter @Column(name="asistencia")
    private boolean asistencia;

    @Getter @Setter @Column(name="validado")
    private boolean validado;
}
