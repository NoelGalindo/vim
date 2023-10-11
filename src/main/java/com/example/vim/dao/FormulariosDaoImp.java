package com.example.vim.dao;

import com.example.vim.models.Formularios;
import freemarker.template.Configuration;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
@RequiredArgsConstructor
public class FormulariosDaoImp implements  FormulariosDao{

    private final EmailServiceDao emailServiceDao;
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

    @Override
    public List<?> getRegisterUsers(int id) {
        String tableName = "eventTable_"+id;
        String query = "FROM "+tableName+" WHERE validado = false";
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<?> getConfirmedUsers(int id) {
        String tableName = "eventTable_"+id;
        String query = "FROM "+tableName+" WHERE validado = true";
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public String confirmRegisterUser(int id_evento, int folio) {
        try{
            String tableName = "eventTable_"+id_evento;
            String query = "UPDATE "+tableName+" t SET t.validado = :newStatus WHERE t.folio = :entityId";
            entityManager.createQuery(query).setParameter("newStatus", true).setParameter("entityId", folio).executeUpdate();
            String queryName = "SELECT nombre, apellido_p, apellido_m, email FROM "+tableName+" WHERE folio = :folio";
            List<Object[]> resultList = entityManager.createQuery(queryName).setParameter("folio", folio).getResultList();
            StringBuilder resultStringBuilder = new StringBuilder();
            String email = "";
            for(Object[] row : resultList){
                String nombre = (String) row[0];
                String apellido_p = (String) row[1];
                String apellido_m = (String) row[2];
                email = (String) row[3];

                resultStringBuilder.append(nombre).append(" ").append(apellido_p).append(" ").append(apellido_m);
            }
            String nombre_completo = resultStringBuilder.toString();
            Map<String, Object> model = new HashMap<>();
            model.put("name", nombre_completo);
            model.put("email", email);
            model.put("folio", folio);
            emailServiceDao.sendValidationMail(model, email);
            return "Exito";
        }catch (Exception e){
            return "Error";
        }
    }

    @Override
    public String refuseRegisterUser(int id_evento, int folio) {
        String tableName = "eventTable_"+id_evento;
        String query = "DELETE "+tableName+" WHERE folio = :entityId";
        entityManager.createQuery(query).setParameter("entityId", folio).executeUpdate();
        return "Exito";
    }


}
