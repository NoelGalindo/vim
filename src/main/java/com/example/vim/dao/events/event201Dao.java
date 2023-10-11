package com.example.vim.dao.events;

import com.example.vim.auth.AuthResponse;
import com.example.vim.auth.LoginUserRequest;
import com.example.vim.models.events.eventTable_201;

public interface event201Dao {
    String registerUser(eventTable_201 usuario);

    AuthResponse loginUser(LoginUserRequest request);

    eventTable_201 data(int folio, String email);

    eventTable_201 certificateData(int folio, String email);

    eventTable_201 userInfo(String email);

    String validateAttendance(int folio);
}
