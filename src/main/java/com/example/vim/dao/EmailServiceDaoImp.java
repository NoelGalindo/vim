package com.example.vim.dao;

import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Repository;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.Map;
import java.util.Properties;

@Repository
@Transactional
public class EmailServiceDaoImp implements EmailServiceDao{
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private Configuration config;
    @Override
    public void sendMail(Map <String, Object> model) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try{
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "UTF-8");
            Template t = config.getTemplate("email-template.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
            message.setFrom("galindo091@gmail.com");
            message.setTo("noelgalindo1999@gmail.com");
            message.setSubject("Mensaje de prueba");
            message.setText(html, true);
            mailSender.send(mimeMessage);
        }catch (Exception ex){
            System.out.println("Email could not be sent to user '{}': {}" + ex.getMessage());
        }



    }
}
