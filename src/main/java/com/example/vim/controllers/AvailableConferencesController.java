package com.example.vim.controllers;

import com.example.vim.dao.Solicitud_formulariosDao;
import com.example.vim.models.ConferencesDTO;
import com.example.vim.models.Solicitud_formularios;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/availabelConferences/")
@RequiredArgsConstructor
public class AvailableConferencesController {
    private final Solicitud_formulariosDao solicitudFormulariosDao;

    @GetMapping("/conferences")
    public List<ConferencesDTO> getConferences(){
        return solicitudFormulariosDao.getConferences();
    }
}
