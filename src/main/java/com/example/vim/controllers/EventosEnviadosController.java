package com.example.vim.controllers;

import com.example.vim.auth.AuthService;
import com.example.vim.auth.AuthUserInfo;
import com.example.vim.dao.EventosEnviadosDao;
import com.example.vim.models.Eventos_enviados;
import com.example.vim.models.Formularios;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/EventosEnviados/")
public class EventosEnviadosController {

    private final AuthService authService;
    private final EventosEnviadosDao eventosEnviadosDao;

    /* GET THE LIST OF PENDENT EVENTS */
    @GetMapping("api/getPendingEvents")
    public ResponseEntity<List<Eventos_enviados>> getPendingEvents(){
        List<Eventos_enviados> pendingEvents = eventosEnviadosDao.getPendingEvents();
        return new ResponseEntity<>(pendingEvents, HttpStatusCode.valueOf(200));
    }

    /* GET THE LIST OF PUBLISHED EVENTS */
    @GetMapping("api/getPublishedEvents")
    public ResponseEntity<List<Eventos_enviados>> getPublishedEvents(){
        List<Eventos_enviados> publishedEvents = eventosEnviadosDao.getPublishedEvents();
        return new ResponseEntity<>(publishedEvents, HttpStatusCode.valueOf(200));
    }

    /* GET THE INFORMATION OF THE FORM */
    @GetMapping("api/getFormInformation/{id}")
    public ResponseEntity<Formularios> getFormInformation(@PathVariable int id){
        return new ResponseEntity<>(eventosEnviadosDao.getFormFromEvent(id), HttpStatusCode.valueOf(200));
    }

    /* CHANGE THE STATUS OF OF THE FORM */
    @PutMapping("api/eventPublished")
    public ResponseEntity<String> publishEvent (@RequestBody String id_evento){
        int id = Integer.parseInt(id_evento);
        eventosEnviadosDao.publishEvent(id);
        return new ResponseEntity<>("Evento publicado correctamente", HttpStatusCode.valueOf(200));
    }

    /* GET THE INFORMATION OF THE USER */
    @PostMapping("/userData")
    public AuthUserInfo userData(@RequestBody String token) {
        return authService.usuarioInfo(token);
    }
}
