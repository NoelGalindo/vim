package com.example.vim.controllers;

import com.example.vim.auth.AuthResponse;
import com.example.vim.auth.LoginUserRequest;
import com.example.vim.dao.EmailServiceDao;
import com.example.vim.dao.ExampleFormDao;
import com.example.vim.models.Example_form;
import com.example.vim.utils.JWTUtil;
import lombok.Getter;

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user/exampleForm/")
@RequiredArgsConstructor
public class ExampleFormController {

    private final ExampleFormDao exampleForm;

    private final JWTUtil jwtUtil;

    private final EmailServiceDao emailService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody LoginUserRequest request){
        AuthResponse response = exampleForm.loginUser(request);
        if(response == null){
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/userInfo")
    public Example_form usarioInfo(@RequestHeader (value = "Authorization") String token){
        String usuario = jwtUtil.getValue(token);
        if(usuario == null){
            return null;
        }
        JSONObject valores = new JSONObject();
        return exampleForm.userInfo(usuario);

    }
    @PostMapping("/api/registerUserExampleForm")
    public void registerUserExample(@RequestBody Example_form data){
        exampleForm.registerUserExample(data);
    }


    // Getting and returning values to create the Qrcode
    @PostMapping(value="/api/dataQrCode")
    public Example_form dataQrCode(@RequestBody String data){
        JSONObject obj = new JSONObject(data);
        String folio = obj.get("folio").toString();
        String email = obj.getString("email").toString();
        Example_form form = exampleForm.dataForQrCode(folio, email);

        return form;
    }

}
