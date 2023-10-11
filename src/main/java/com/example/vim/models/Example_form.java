package com.example.vim.models;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Example_form {

    @Id
    @Column(name="folio")
    @GeneratedValue(strategy= GenerationType.IDENTITY) // Que se genere automaticamente el id con el AutoIncrement
    private int folio;

    @Column(name="id_evento")
    private int id_evento;

    @Column(name="Nombre")
    private String Nombre;

    @Column(name="Apellido_P")
    private String Apellid_P;

    @Column(name="Apellido_M")
    private String apellido_m;

    @Column(name="Edad")
    private int Edad;

    @Column(name = "Soltero")
    private boolean Soltero;

    @Column(name = "Casado")
    private boolean Casado;

    @Column(name="Subscrito")
    private boolean Subscrito;

    @Column(name="Ejercicio")
    private boolean Ejercicio;

    @Column(name="Email")
    private String Email;

    @Column(name="Fecha")
    private Date Fecha;

    @Column(name="Voucher")
    private String Voucher;

    @Column(name="Asistencia")
    private boolean Asistencia;

    @Column(name="Validado")
    private boolean Validado;
}
