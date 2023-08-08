package com.example.vim.dao;

import com.example.vim.models.Example_form;
import com.example.vim.models.Solicitud_formularios;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Repository;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class ExampleFormDaoImp implements ExampleFormDao{
    @Autowired
    EntityManager entityManager;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private Configuration config;
    @Override
    public void registerUserExample(Example_form data) {
        data.setId_formulario(1);
        entityManager.merge(data);
    }

    @Override
    public List<Example_form> getRegisterUsers(Long id) {
        String query = "FROM Example_form WHERE validado = false";
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void validateRegisterUser(Long id) {
        Example_form form = entityManager.find(Example_form.class, id);
        form.setValidado(true);
        sendValidationEmail(form);
        entityManager.merge(form);
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

    @Override
    public void refuseRegisterUser(Long id) {
        Example_form form = entityManager.find(Example_form.class, id);
        entityManager.remove(form);
    }

    @Override
    public Example_form dataForQrCode(String folio, String email) {
        String query = "FROM Example_form WHERE folio = :folio";
        List <Example_form> data = entityManager.createQuery(query).setParameter("folio", folio).getResultList();
        if(!data.isEmpty() ) {
            Example_form form = data.get(0);
            if (form.getEmail().equals(email)) {
                return form;
            }
        }

        return null;

    }

    @Override
    public List<Example_form> getConfirmedUsersData(Long id) {
        String query = "FROM Example_form WHERE validado = true";
        return entityManager.createQuery(query).getResultList();
    }


}
