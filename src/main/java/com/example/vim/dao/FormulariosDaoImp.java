package com.example.vim.dao;

import com.example.vim.models.Formularios;
import freemarker.template.Configuration;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class FormulariosDaoImp implements  FormulariosDao{

    private final JavaMailSender mailSender;
    private final Configuration config;
    private final EntityManager entityManager;
    @Override
    public void createForm(Formularios form) {
        form.setContador_cupo(0);
        entityManager.merge(form);
    }

    @Override
    public List<Formularios> getAllForms(int id_evento) {
        String query = "FROM Formularios WHERE id_evento = :evento";
        List<Formularios> forms = entityManager.createQuery(query).setParameter("evento", id_evento).getResultList();
        return forms;
    }

    @Override
    public void deleteForm(Long id_formulario) {
        Formularios form = entityManager.find(Formularios.class, id_formulario);
        entityManager.remove(form);
    }

    @Override
    public Formularios getForm(Long id_formulario) {
        return entityManager.find(Formularios.class, id_formulario);
    }

    @Override
    public void updateForm(Formularios form) {
        entityManager.merge(form);
    }



}
