package com.example.vim.dao;

import com.example.vim.models.Formularios_enviados;
import com.example.vim.models.Solicitud_formularios;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class Formularios_enviadosDaoImp implements Formularios_enviadosDao{
    @Autowired
    EntityManager entityManager;
    @Override
    public List<Formularios_enviados> showForms() {
        String query = "FROM Formularios_enviados WHERE estado='PENDIENTE'";
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Formularios_enviados> publishedForms() {
        String query = "FROM Formularios_enviados WHERE estado='PUBLICADO'";
        return entityManager.createQuery(query).getResultList();

    }

    @Override
    public Solicitud_formularios getForm(Long id) {
        String query = "FROM Solicitud_formularios WHERE id_formulario = :id";
        List <Solicitud_formularios> data = entityManager.createQuery(query).setParameter("id", id).getResultList();
        if(data == null)
            return null;
        return data.get(0);
    }

    @Override
    public void changeStatus(Long id) {
        String query = "FROM Solicitud_formularios WHERE id_formulario = :id";
        List <Solicitud_formularios> data = entityManager.createQuery(query).setParameter("id", id).getResultList();
        if(data != null){
            Solicitud_formularios formulario = data.get(0);
            // Once we have the form we change the status and save the new state
            formulario.setStatus("Listo");
            entityManager.merge(formulario);
        }
        query = "FROM Formularios_enviados WHERE id_formulario = :id";
        List <Formularios_enviados> forms = entityManager.createQuery(query).setParameter("id", id).getResultList();
        if(data != null){
            Formularios_enviados sendForm = forms.get(0);
            // Once we have the form we change the status and save the new state
            sendForm.setEstado("PUBLICADO");
            entityManager.merge(sendForm);
        }
    }
}
