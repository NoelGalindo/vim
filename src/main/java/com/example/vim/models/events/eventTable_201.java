package com.example.vim.models.events;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "eventtable_201")
public class eventTable_201 {
    @Id
    @Column(name="folio")
    @GeneratedValue(strategy= GenerationType.IDENTITY) // Que se genere automaticamente el id con el AutoIncrement
    private int folio;

    @Column(name="id_evento")
    private int id_evento;

    @Column(name="Nombre")
    private String nombre;

    @Column(name="Apellido_P")
    private String apellido_p;

    @Column(name="Apellido_M")
    private String apellido_m;

    @Column(name="Email")
    private String email;

    @Column(name = "Categoria")
    private String categoria;

    @Column(name = "Credencial")
    private String credencial;

    @Column(name = "Nivel_Educativo")
    private String nivel_educativo;

    @Column(name = "Estado")
    private String estado;

    @Column(name = "Institucion")
    private String institucion;

    @Column(name="Voucher")
    private String voucher;

    @Column(name="Asistencia")
    private boolean asistencia;

    @Column(name="Validado")
    private boolean validado;
}
