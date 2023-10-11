package com.example.vim.dao;

import com.example.vim.models.Eventos;
import com.example.vim.models.Eventos_enviados;
import com.example.vim.models.Formularios;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional
@RequiredArgsConstructor
public class EventosEnviadosDaoImp implements EventosEnviadosDao{

    private final EntityManager entityManager;
    @Override
    public List<Eventos_enviados> getPendingEvents() {
        String query = "Select ee.id_evento, ee.username, ee.fecha From Eventos_enviados ee inner join Eventos e on ee.id_evento = e.id_evento where e.status = 'En revision'";
        List<Eventos_enviados> pendingEvents = entityManager.createQuery(query).getResultList();
        return pendingEvents;
    }

    @Override
    public List<Eventos_enviados> getPublishedEvents() {
        String query = "Select ee.id_evento, ee.username, ee.fecha From Eventos_enviados ee inner join Eventos e on ee.id_evento = e.id_evento where e.status = 'Publicado'";
        List<Eventos_enviados> publishedEvents = entityManager.createQuery(query).getResultList();
        return publishedEvents;
    }

    @Override
    public Formularios getFormFromEvent(int id_evento) {
        String query = "FROM Formularios WHERE id_evento = :id";
        List<Formularios> response = entityManager.createQuery(query).setParameter("id", id_evento).getResultList();
        if(response.isEmpty()){
            return null;
        }
        return response.get(0);
    }

    @Override
    public void publishEvent(int id_evento) {
        Eventos event = entityManager.find(Eventos.class, id_evento);
        if(event != null){
            event.setStatus("Publicado");
            entityManager.merge(event);
        }

    }


}
