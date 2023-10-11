package com.example.vim.auth;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserRequest {

    int folio;
    String email;
}
