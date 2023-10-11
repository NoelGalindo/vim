package com.example.vim.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="eventos_enviados")
public class Eventos_enviados {
    @Id
    @Column(name="id_evento")
    int id_evento;
    @Column(name="username")
    String username;
    @Column(name="fecha")
    LocalDate fecha;
}
