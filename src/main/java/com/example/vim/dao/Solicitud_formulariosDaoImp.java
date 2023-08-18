package com.example.vim.dao;


import com.example.vim.models.ConferencesDTO;
import com.example.vim.models.Example_form;
import com.example.vim.models.Formularios_enviados;
import com.example.vim.models.Solicitud_formularios;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.sqm.UnknownEntityException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
@RequiredArgsConstructor
public class Solicitud_formulariosDaoImp implements Solicitud_formulariosDao{

    private final JavaMailSender mailSender;
    private final Configuration config;

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

    @Override
    public List<Example_form> getRegisterUsers(String tableName) {

        String query = "FROM "+tableName+ " WHERE validado = false";
        List<Example_form> lista = entityManager.createQuery(query).getResultList();
        return lista;

    }

    @Override
    public void validateRegisterUser(Long folio, String tableName) {
        String query = "FROM "+tableName+ " WHERE folio = :id";
        List<Example_form> form = entityManager.createQuery(query).setParameter("id", folio).getResultList();
        if(form != null){
            form.get(0).setValidado(true);
            sendValidationEmail(form.get(0));
            entityManager.merge(form.get(0));
        }

    }

    @Override
    public void refuseRegisterUser(Long folio, String tableName) {
        String query = "FROM "+tableName+ " WHERE folio = :id";
        List<Example_form> form = entityManager.createQuery(query).setParameter("id", folio).getResultList();
        if(form != null){
            entityManager.remove(form.get(0));
        }
    }

    @Override
    public List<Example_form> getConfirmedUsersData(String tableName) {
        String query = "FROM "+tableName+ " WHERE validado = true";
        List<Example_form> lista = entityManager.createQuery(query).getResultList();
        return lista;
    }

    @Override
    public void sendValidationEmail(Example_form form) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        Map<String, Object> model = new HashMap<>();
        String nombre = form.getNombre()+" "+form.getApellid_p()+" "+form.getApellido_m();
        String folio = ""+form.getFolio();
        model.put("name", nombre);
        model.put("folio", folio);
        String to = form.getEmail();

        try{
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "UTF-8");
            Template t = config.getTemplate("email-template.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
            message.setFrom("galindo091@gmail.com");
            message.setTo(to);
            message.setSubject("Información validada");
            message.setText(html, true);
            System.out.println(to);
            mailSender.send(mimeMessage);
        }catch (Exception ex){
            System.out.println("Email could not be sent to user '{}': {}" + ex.getMessage());
        }
    }


}
