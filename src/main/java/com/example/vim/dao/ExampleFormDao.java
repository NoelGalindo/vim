package com.example.vim.dao;

import com.example.vim.auth.AuthResponse;
import com.example.vim.auth.LoginUserRequest;
import com.example.vim.models.Example_form;

import java.util.List;
import java.util.Map;

public interface ExampleFormDao {

    AuthResponse loginUser(LoginUserRequest request);
    void registerUserExample(Example_form data);

    List<Example_form> getRegisterUsers(Long id);

    void validateRegisterUser(Long id);

    void refuseRegisterUser(Long id);

    Example_form dataForQrCode(String folio, String email);

    List<Example_form> getConfirmedUsersData (Long id);

    void sendValidationEmail(Example_form form);
}
