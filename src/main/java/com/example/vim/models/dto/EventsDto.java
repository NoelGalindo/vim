package com.example.vim.models.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class EventsDto {
    private String direccion_url;
    private String nombre_evento;
    private String informacion_evento;
    private Integer cupo_maximo;
    private String imagen_evento;
}
