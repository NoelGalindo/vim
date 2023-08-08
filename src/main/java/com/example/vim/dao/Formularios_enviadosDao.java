package com.example.vim.dao;

import com.example.vim.models.Formularios_enviados;
import com.example.vim.models.Solicitud_formularios;

import java.text.Normalizer;
import java.util.List;

public interface Formularios_enviadosDao {
    List<Formularios_enviados> showForms();

    List<Formularios_enviados> publishedForms();

    Solicitud_formularios getForm(Long id);

    void changeStatus(Long id);
}
