package com.example.vim.dao;

import java.util.Map;

public interface EmailServiceDao {

    void sendMail(Map<String, Object> model);

}
