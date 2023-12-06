package com.example.vim.dao;

import com.example.vim.models.Formularios;
import com.example.vim.models.events.eventTable_201;
import freemarker.template.Configuration;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
    public Workbook attendanceList(int id) {
        String tableName = "eventTable_" + id;
        String query = "SELECT folio, nombre, apellido_p, apellido_m, asistencia FROM " + tableName + " WHERE asistencia = true AND validado = true";
        List<Object[]> attendance = entityManager.createQuery(query).getResultList();

        Workbook workook = new XSSFWorkbook();
        Sheet sheet = workook.createSheet("Asistencia");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Folio");
        headerRow.createCell(1).setCellValue("Nombre");
        headerRow.createCell(2).setCellValue("Apellido P");
        headerRow.createCell(3).setCellValue("Apellido M");
        headerRow.createCell(4).setCellValue("Asistencia");

        if(!attendance.isEmpty()){
            int rowNumber = 1;

            for (Object[] object : attendance){
                int folio = (int) object[0];
                String nombre = (String) object[1];
                String apellido_p = (String) object[2];
                String apellido_m = (String) object[3];
                boolean asistencia = (boolean) object[4];
                String valor = "";
                if(asistencia == true)
                    valor = "X";
                else
                    valor = "-";

                Row valueRow = sheet.createRow(rowNumber);

                valueRow.createCell(0).setCellValue(folio);
                valueRow.createCell(1).setCellValue(nombre);
                valueRow.createCell(2).setCellValue(apellido_p);
                valueRow.createCell(3).setCellValue(apellido_m);
                valueRow.createCell(4).setCellValue(valor);

                rowNumber++;
            }
        }

        return workook;
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

    @Override
    public String markAttendance(int id_evento, int folio) {
        String tableName = "eventTable_"+id_evento;
        String query = "FROM "+tableName+" WHERE folio = :folio";
        List<?> lista = entityManager.createQuery(query).setParameter("folio", folio).getResultList();
        if(!lista.isEmpty()){
            String queryUpdate = "UPDATE "+tableName+" t SET t.asistencia = :newStatus WHERE t.folio = :entityId";
            entityManager.createQuery(queryUpdate).setParameter("newStatus", true).setParameter("entityId", folio).executeUpdate();
            return "Exito";
        }
        return "Error";
    }


}
