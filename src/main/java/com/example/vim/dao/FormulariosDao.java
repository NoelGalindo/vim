package com.example.vim.dao;

import com.example.vim.models.Formularios;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

public interface FormulariosDao {

    void createForm(Formularios form);
    List<Formularios> getAllForms(int id_evento);
    void deleteForm(Long id_formulario);

    Formularios getForm(Long id_formulario);

    void updateForm(Formularios form);

    List<?> getRegisterUsers(int id);

    List<?> getConfirmedUsers(int id);

    Workbook attendanceList(int id);

    String confirmRegisterUser(int id_evento, int folio);

    String refuseRegisterUser(int id_evento, int folio);

    String markAttendance(int id_evento, int folio);

}
