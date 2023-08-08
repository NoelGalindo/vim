package com.example.vim.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ConferencesDTO {
    private String direccion_url;
    private String nombre_formulario;
    private String informacion_formulario;
    private Integer cupo_maximo;
}
