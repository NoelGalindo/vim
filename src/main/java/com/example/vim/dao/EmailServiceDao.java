package com.example.vim.dao;

import java.util.HashMap;
import java.util.Map;

public interface EmailServiceDao {

    void sendValidationMail(Map<String, Object> data, String destination);

}
