package com.example.vim.dao;

import com.example.vim.models.Eventos;
import com.example.vim.models.Eventos_enviados;
import com.example.vim.models.dto.EventsDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class EventosDaoImp implements EventosDao{
    private final EntityManager entityManager;

    @Override
    public void addEvent(Eventos event) {
        event.setStatus("Creado");
        entityManager.merge(event);
    }

    @Override
    public List getAllEvents(String username) {
        String query = "FROM Eventos WHERE username = :usr";
        return entityManager.createQuery(query).setParameter("usr", username).getResultList();
    }

    @Override
    public void deleteEvent(int id) {
        Eventos event = entityManager.find(Eventos.class, id);
        entityManager.remove(event);
    }

    @Override
    public void senEvent(int id_event) {
        Eventos event = entityManager.find(Eventos.class, id_event);
        LocalDate date = LocalDate.now();
        Eventos_enviados sendEvent = new Eventos_enviados(id_event, event.getUsername(), date);
        entityManager.merge(sendEvent);
        event.setStatus("En revision");
        entityManager.merge(event);
    }

    @Override
    public void addImageCertificate(int id_evento, String imagen_constancia) {
        Eventos event = entityManager.find(Eventos.class, id_evento);
        event.setImg_constancia(imagen_constancia);
        entityManager.merge(event);
    }

    @Override
    public String getCertificate(int id_evento) {
        Eventos event = entityManager.find(Eventos.class, id_evento);
        if(event.getImg_constancia().isEmpty()){
            return  "NO CONTENT";
        }
        return  event.getImg_constancia();
    }

    @Override
    public int eventMaxCapacity(int id_evento) {
        Eventos event = entityManager.find(Eventos.class, id_evento);
        return event.getCupo_maximo();
    }

    @Override
    public List getPublishedEvents() {
        String query = "SELECT direccion_url, nombre_evento, informacion_evento, cupo_maximo, imagen_evento FROM Eventos WHERE status='Publicado'";
        return entityManager.createQuery(query).getResultList();
    }


}
