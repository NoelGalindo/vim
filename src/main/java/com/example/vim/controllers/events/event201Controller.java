package com.example.vim.controllers.events;

import com.example.vim.auth.AuthResponse;
import com.example.vim.auth.LoginUserRequest;
import com.example.vim.models.Example_form;
import com.example.vim.models.events.eventTable_201;
import com.example.vim.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.vim.dao.events.event201Dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/user/event201/")
@RequiredArgsConstructor
public class event201Controller {

    private final event201Dao event201Dao;
    private final JWTUtil jwtUtil;

    @PostMapping("api/register")
    public ResponseEntity<String> register(@RequestBody eventTable_201 user){
        String status = event201Dao.registerUser(user);
        return new ResponseEntity<>(status, HttpStatus.valueOf(200));
    }

    @PostMapping("api/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody LoginUserRequest request){
        AuthResponse response = event201Dao.loginUser(request);
        if(response == null){
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/userInfo")
    public eventTable_201 usarioInfo(@RequestHeader(value = "Authorization") String token){
        String usuario = jwtUtil.getValue(token);
        if(usuario == null){
            return null;
        }
        JSONObject valores = new JSONObject();
        return event201Dao.userInfo(usuario);
    }

    // Getting and returning values to create the Qrcode
    @PostMapping(value="/api/generalData")
    public eventTable_201 dataQrCode(@RequestBody String data){
        JSONObject obj = new JSONObject(data);
        String folio = obj.get("folio").toString();
        String email = obj.getString("email").toString();
        return event201Dao.data(Integer.parseInt(folio), email);
    }

    @PostMapping("api/certificateData")
    public eventTable_201 certificateData(@RequestBody String data){
        JSONObject obj = new JSONObject(data);
        String folio = obj.get("folio").toString();
        String email = obj.getString("email").toString();
        return event201Dao.certificateData(Integer.parseInt(folio), email);
    }


    @PutMapping("api/validateAttendance")
    public ResponseEntity<String> validateAttendance(@RequestBody String id){
        int folio = Integer.parseInt(id);
        String response = event201Dao.validateAttendance(folio);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(200));
    }

    @GetMapping("api/validacion/{id}")
    public ResponseEntity<List<?>> validation(@PathVariable String id){
        int folio = Integer.parseInt(id);
        List<?> response = event201Dao.validation(folio);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(200));
    }

}
