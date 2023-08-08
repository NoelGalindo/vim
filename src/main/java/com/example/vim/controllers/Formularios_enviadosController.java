package com.example.vim.controllers;

import com.example.vim.auth.AuthService;
import com.example.vim.auth.AuthUserInfo;
import com.example.vim.dao.Formularios_enviadosDao;
import com.example.vim.dao.Solicitud_formulariosDao;
import com.example.vim.models.Formularios_enviados;
import com.example.vim.models.Solicitud_formularios;
import com.example.vim.utils.JWTUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class Formularios_enviadosController {

    private final AuthService authService;
    private final Formularios_enviadosDao formEnviadosDao;
    private final JWTUtil jwtUtil;

    @PostMapping(value = "api/pendingForms")
    public List<Formularios_enviados> showForm(){
        return formEnviadosDao.showForms();
    }

    @GetMapping("api/publishedForms")
    public List<Formularios_enviados> publishedForms(){
        return formEnviadosDao.publishedForms();
    }

    @GetMapping(value = "api/seeFormStructure/{id}")
    public Solicitud_formularios getForm(@PathVariable Long id){
        return formEnviadosDao.getForm(id);
    }

    @GetMapping(value = "api/downloadForm/{id}")
    public Solicitud_formularios formDownload(@PathVariable Long id){
        return formEnviadosDao.getForm(id);
    }
    @PostMapping("api/completeForm")
    public void completeForm(@RequestBody String id){
        Long id_form = Long.parseLong(id);
        formEnviadosDao.changeStatus(id_form);
    }
    @PostMapping("/userData")
    public AuthUserInfo userData(@RequestBody String token){
        return authService.usuarioInfo(token);
    }
}
