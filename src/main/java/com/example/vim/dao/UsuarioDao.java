package com.example.vim.dao;

import com.example.vim.auth.AuthUserInfo;
import com.example.vim.models.User;

public interface UsuarioDao {

    User obtenerCredencialesUsuario(User usuario);

}
