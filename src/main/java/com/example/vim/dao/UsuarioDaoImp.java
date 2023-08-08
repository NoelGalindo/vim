package com.example.vim.dao;

import com.example.vim.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class UsuarioDaoImp implements  UsuarioDao{

    @PersistenceContext
    private EntityManager entityManager; // It's used to connect with the dato on the database.
    @Override
    public User obtenerCredencialesUsuario(User usuario) {
        String query = "FROM Usuarios WHERE usuario = :usuario"; // Trabaja sobre la clase de java, no de la base de datos
        List<User> lista = entityManager.createQuery(query)
                .setParameter("usuario", usuario.getUsername()).getResultList();
        if(!lista.isEmpty()){
            String passwordH = lista.get(0).getPassword();

            if(passwordH.contentEquals(usuario.getPassword())){
                return lista.get(0);
            }
            return null;
        }
        return null;
    }
}
