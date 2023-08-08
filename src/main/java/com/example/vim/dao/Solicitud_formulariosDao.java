package com.example.vim.dao;

import com.example.vim.models.ConferencesDTO;
import com.example.vim.models.Formularios_enviados;
import com.example.vim.models.Solicitud_formularios;

import java.util.List;

public interface Solicitud_formulariosDao {
    List<Solicitud_formularios> getFormularios(String username);

    void eliminarFormulario(Long id);

    void agregarFormulario(Solicitud_formularios form);

    Solicitud_formularios getFormData(Long id);

    void changeForm(Solicitud_formularios form);

    void changeStatusForm(Formularios_enviados date, String status);

    List<ConferencesDTO> getConferences();
}
