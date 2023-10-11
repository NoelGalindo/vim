package com.example.vim.dao;

import com.example.vim.auth.AuthResponse;
import com.example.vim.auth.LoginUserRequest;
import com.example.vim.models.Example_form;
import com.example.vim.models.Role;
import com.example.vim.utils.JWTUtil;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class ExampleFormDaoImp implements ExampleFormDao {
    private final EntityManager entityManager;
    private final JWTUtil jwtUtil;

    @Override
    public AuthResponse loginUser(LoginUserRequest request) {
        String query = "FROM Example_form WHERE folio = :folio AND validado = true"; // Trabaja sobre la clase de java, no de la base de datos
        List<Example_form> lista = entityManager.createQuery(query).setParameter("folio", request.getFolio()).getResultList();
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
    public void registerUserExample(Example_form data) {
        entityManager.merge(data);
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
    public Example_form userInfo(String email) {
        String query = "FROM Example_form WHERE email = :email";
        List <Example_form> data = entityManager.createQuery(query).setParameter("email", email).getResultList();
        if(!data.isEmpty() ) {
            return data.get(0);
        }

        return null;
    }


}
