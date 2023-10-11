package com.example.vim.dao;

import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import org.springframework.stereotype.Repository;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.Map;


@Repository
@Transactional
@RequiredArgsConstructor
public class EmailServiceDaoImp implements EmailServiceDao{

    private final JavaMailSender mailSender;
    private final Configuration config;

    @Override
    public void sendValidationMail(Map<String, Object> data, String destination) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try{
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "UTF-8");
            Template t = config.getTemplate("email-encurso.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, data);
            message.setFrom("galindo091@gmail.com");
            message.setTo(destination);
            message.setSubject("Información validada");
            message.setText(html, true);
            mailSender.send(mimeMessage);
        }catch (Exception ex){
            System.out.println("Email could not be sent to user '{}': {}" + ex.getMessage());
        }
    }
}
