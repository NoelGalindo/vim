package com.example.vim.models;

import jakarta.persistence.*;
import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="eventos")
public class Eventos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evento")
    int id_evento;
    @Column(name = "username")
    String username;
    @Column(name = "direccion_url")
    String direccion_url;
    @Column(name = "nombre_evento")
    String nombre_evento;
    @Column(name = "informacion_evento")
    String informacion_evento;
    @Column(name = "imagen_evento")
    String imagen_evento;
    @Column(name = "cupo_maximo")
    int cupo_maximo;
    @Column(name = "status")
    String status;
    @Column(name="img_constancia")
    String img_constancia;
}
