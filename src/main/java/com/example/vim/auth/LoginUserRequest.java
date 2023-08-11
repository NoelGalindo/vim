package com.example.vim.auth;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserRequest {

    long folio;
    String email;
}
