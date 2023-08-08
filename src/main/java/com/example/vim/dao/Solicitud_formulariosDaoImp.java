package com.example.vim.dao;


import com.example.vim.models.ConferencesDTO;
import com.example.vim.models.Formularios_enviados;
import com.example.vim.models.Solicitud_formularios;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class Solicitud_formulariosDaoImp implements Solicitud_formulariosDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Solicitud_formularios> getFormularios(String username) {
        String query = "FROM Solicitud_formularios WHERE username = :usr";
        return entityManager.createQuery(query).setParameter("usr", username).getResultList();
    }

    @Override
    public void eliminarFormulario(Long id) {
        Solicitud_formularios form = entityManager.find(Solicitud_formularios.class, id);
        entityManager.remove(form);
    }

    @Override
    public void agregarFormulario(Solicitud_formularios form) {
        form.setContador_cupo(0);
        entityManager.merge(form);
    }

    @Override
    public Solicitud_formularios getFormData(Long id) {
        String query = "FROM Solicitud_formularios WHERE id_formulario = :id";
        List <Solicitud_formularios> data = entityManager.createQuery(query).setParameter("id", id).getResultList();
        if(data == null)
            return null;
        return data.get(0);
    }

    @Override
    public void changeForm(Solicitud_formularios form) {
        String query = "FROM Solicitud_formularios WHERE id_formulario = :id";
        List <Solicitud_formularios> data = entityManager.createQuery(query).setParameter("id", form.getId_formulario()).getResultList();
        if(data == null){

        }else{
            form.setStatus("Creado");
            form.setContador_cupo(0);
            entityManager.merge(form);

        }
    }

    @Override
    public void changeStatusForm(Formularios_enviados send, String status) {
        // We need to change the status of the Form in the table solicitud formularios
        String query = "FROM Solicitud_formularios WHERE id_formulario = :id";
        List <Solicitud_formularios> data = entityManager.createQuery(query).setParameter("id", send.getId_formulario()).getResultList();
        if(data != null){
            Solicitud_formularios formulario = data.get(0);
            // Once we have the form we change the status and save the new state
            formulario.setStatus(status);
            entityManager.merge(formulario);
        }
        // And now we can send the form to the table
        entityManager.merge(send);
    }

    // USE TO GET THE PUBLISHED CONFERENCES
    @Override
    public List<ConferencesDTO> getConferences() {
        String query="SELECT direccion_url, nombre_formulario, informacion_formulario, cupo_maximo FROM Solicitud_formularios WHERE status = 'Listo' ";
        List<ConferencesDTO> conferences = entityManager.createQuery(query).getResultList();
        return  conferences;
    }

}
