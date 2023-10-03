package com.example.vim.dao;

import com.example.vim.models.Eventos;
import com.example.vim.models.dto.EventsDto;

import java.util.List;

public interface EventosDao {

    void addEvent(Eventos event);
    List<Eventos> getAllEvents (String username);

    void deleteEvent(int id);

    void senEvent(int id_event);

    void addImageCertificate(int id_evento, String imagen_constancia);

    String getCertificate(int id_evento);

    int eventMaxCapacity(int id_evento);

    List<EventsDto> getPublishedEvents();
}
