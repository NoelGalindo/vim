package com.example.vim.controllers;

import com.example.vim.auth.AuthService;
import com.example.vim.auth.AuthUserInfo;
import com.example.vim.dao.EventosDao;
import com.example.vim.models.Eventos;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventos/")
@RequiredArgsConstructor
@PreAuthorize("hasRole('MANAGER')")
public class EventosController {

    private final AuthService authService;
    private final EventosDao eventosDao;

    /* CREATE THE EVENT */
    @PostMapping("api/addEvent")
    public ResponseEntity<String> createEvent(@RequestBody Eventos evento) {
        eventosDao.addEvent(evento);
        return new ResponseEntity<>("Creado correctamente", HttpStatusCode.valueOf(200));
    }

    /* GET THE LIST OF ALL THE EVENTS */
    @PostMapping(value = "api/getEvents")
    public ResponseEntity<List<Eventos>> getAllEvents(@RequestBody String token) {
        String username = authService.usuarioUsername(token);
        List<Eventos> events = eventosDao.getAllEvents(username);
        return new ResponseEntity<>(events, HttpStatusCode.valueOf(200));
    }

    /* DELETE THE EVENT */
    @DeleteMapping("api/deleteEvent/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable int id) {
        eventosDao.deleteEvent(id);
        return new ResponseEntity<>("Eliminado correctament", HttpStatusCode.valueOf(200));
    }

    /* ADD CERTIFICATE IMAGE TO THE EVENT TABLE */
    @PutMapping("api/certificateUrl")
    public ResponseEntity<String> certificateUrl(@RequestBody String information) {
        JSONObject objeto = new JSONObject(information);
        int id_evento = Integer.parseInt(objeto.getString("id_evento"));
        String imagen_constancia = objeto.getString("imagen_constancia");
        eventosDao.addImageCertificate(id_evento, imagen_constancia);
        return new ResponseEntity<>("Constancia actualizado correctamente", HttpStatusCode.valueOf(200));
    }

    /* CHECK IF THE EVENT HAS A CERTIFICATE IMAGE */
    @PostMapping("api/getCertificate")
    public ResponseEntity<String> getCertificate(@RequestBody int id_evento){
        String response = eventosDao.getCertificate(id_evento);
        return  new ResponseEntity<>(response, HttpStatusCode.valueOf(200));
    }

    /* SEND(CHANGE THE STATUS OF THE EVENT AND SEND IT TO EVENTOS_ENVIADOS)*/
    @PutMapping("api/sendEvent")
    public ResponseEntity<String> sendEvent(@RequestBody String id_event) {
        eventosDao.senEvent(Integer.parseInt(id_event));
        return new ResponseEntity<>("Evento enviado para su revisión", HttpStatusCode.valueOf(200));
    }

    /* GET THE INFORMATION OF THE USER */
    @PostMapping("/userData")
    public AuthUserInfo userData(@RequestBody String token) {
        return authService.usuarioInfo(token);
    }
}
