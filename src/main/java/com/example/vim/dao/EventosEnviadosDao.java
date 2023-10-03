package com.example.vim.dao;

import com.example.vim.models.Eventos_enviados;
import com.example.vim.models.Formularios;

import java.util.List;

public interface EventosEnviadosDao {

    List<Eventos_enviados> getPendingEvents();

    List<Eventos_enviados> getPublishedEvents();

    Formularios getFormFromEvent(int id_evento);

    void publishEvent(int id_evento);
}
