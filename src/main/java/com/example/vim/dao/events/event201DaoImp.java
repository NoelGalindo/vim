package com.example.vim.dao.events;

import com.example.vim.auth.AuthResponse;
import com.example.vim.auth.LoginUserRequest;
import com.example.vim.models.Example_form;
import com.example.vim.models.Role;
import com.example.vim.models.events.eventTable_201;
import com.example.vim.utils.JWTUtil;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class event201DaoImp implements event201Dao{

    private final EntityManager entityManager;
    private final JWTUtil jwtUtil;

    @Override
    public String registerUser(eventTable_201 usuario) {
        String query = "FROM eventTable_201 WHERE email = :email";
        List<eventTable_201> users = entityManager.createQuery(query).setParameter("email", usuario.getEmail()).getResultList();
        if(users.isEmpty()){
            entityManager.merge(usuario);
            return "Registrado";
        }
        return "Duplicado";
    }

    @Override
    public AuthResponse loginUser(LoginUserRequest request) {
        String query = "FROM eventTable_201 WHERE folio = :folio AND validado = true"; // Trabaja sobre la clase de java, no de la base de datos
        List<eventTable_201> lista = entityManager.createQuery(query).setParameter("folio", request.getFolio()).getResultList();
        if (!lista.isEmpty()) {
            String email = lista.get(0).getEmail();

            if (email.contentEquals(request.getEmail())) {
                String folio = String.valueOf(lista.get(0).getFolio());
                String token = jwtUtil.create(folio, email);
                return AuthResponse.builder()
                        .token(token)
                        .role(Role.USER)
                        .build();
            }
            return null;
        }
        return null;
    }

    @Override
    public eventTable_201 data(int folio, String email) {
        String query = "FROM eventTable_201 WHERE folio = :folio";
        List <eventTable_201> data = entityManager.createQuery(query).setParameter("folio", folio).getResultList();
        if(!data.isEmpty() ) {
            eventTable_201 form = data.get(0);
            if (form.getEmail().equals(email)) {
                return form;
            }
        }

        return null;
    }

    @Override
    public eventTable_201 certificateData(int folio, String email) {
        String query = "FROM eventTable_201 WHERE folio = :folio AND asistencia = true";
        List <eventTable_201> data = entityManager.createQuery(query).setParameter("folio", folio).getResultList();
        if(!data.isEmpty() ) {
            eventTable_201 form = data.get(0);
            if (form.getEmail().equals(email)) {
                return form;
            }
        }

        return null;
    }

    @Override
    public eventTable_201 userInfo(String email) {
        String query = "FROM eventTable_201 WHERE email = :email AND validado = true";
        List <eventTable_201> data = entityManager.createQuery(query).setParameter("email", email).getResultList();
        if(!data.isEmpty() ) {
            return data.get(0);
        }
        return null;
    }

    @Override
    public String validateAttendance(int folio) {
        String query = "FROM eventTable_201 WHERE folio = :folio";
        List<?> lista = entityManager.createQuery(query).setParameter("folio", folio).getResultList();
        if(!lista.isEmpty()){
            eventTable_201 event = entityManager.find(eventTable_201.class, folio);
            event.setAsistencia(true);
            entityManager.merge(event);
            return "Exito";
        }
        return "Error";
    }
}
