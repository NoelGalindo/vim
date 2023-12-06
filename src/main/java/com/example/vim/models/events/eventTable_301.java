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
@Table(name = "eventtable_301")
public class eventTable_301 {
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

    @Column(name="Voucher")
    private String voucher;

    @Column(name="Asistencia")
    private boolean asistencia;

    @Column(name="Validado")
    private boolean validado;

}
