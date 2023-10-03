package com.example.vim.controllers;

import com.example.vim.dao.EventosDao;
import com.example.vim.dao.FormulariosDao;
import com.example.vim.models.Formularios;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.Normalizer;
import java.util.List;

@RestController
@RequestMapping("/formularios/")
@RequiredArgsConstructor
@PreAuthorize("hasRole('MANAGER')")
public class FormulariosController {

    private final FormulariosDao formulariosDao;
    private final EventosDao eventosDao;

    @PostMapping("api/createForm")
    public ResponseEntity<String> createForm(@RequestBody Formularios form){
        formulariosDao.createForm(form);

        return new ResponseEntity<>("Formulario creado correctamente", HttpStatusCode.valueOf(200));
    }

    @PostMapping("api/getAllForms")
    public ResponseEntity<List<Formularios>> getAllForms(@RequestBody int id_evento){
        List<Formularios> forms = formulariosDao.getAllForms(id_evento);
        return new ResponseEntity<>(forms, HttpStatusCode.valueOf(200));
    }

    @DeleteMapping("api/deleteForm/{id}")
    public ResponseEntity<String> deleteForm(@PathVariable Long id){
        formulariosDao.deleteForm(id);
        return new ResponseEntity<>("Formulario eliminado correctamente", HttpStatusCode.valueOf(200));
    }

    @GetMapping("api/getForm/{id}")
    public ResponseEntity<Formularios> getForm(@PathVariable Long id){
        return new ResponseEntity<>(formulariosDao.getForm(id), HttpStatusCode.valueOf(200));
    }

    @PutMapping("api/updateForm")
    public ResponseEntity<String> updateForm(@RequestBody Formularios form){
        formulariosDao.updateForm(form);
        return new ResponseEntity<>("Actualizado correctamente", HttpStatusCode.valueOf(200));
    }

    @GetMapping("api/eventMaxCapacity/{id}")
    public ResponseEntity<Integer> eventMaxCapacity(@PathVariable int id){
        return new ResponseEntity<>(eventosDao.eventMaxCapacity(id), HttpStatusCode.valueOf(200));
    }
}
